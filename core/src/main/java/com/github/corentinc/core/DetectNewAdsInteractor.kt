package com.github.corentinc.core

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.repository.GlobalSharedPreferencesRepository
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.repository.searchAdsPosition.SearchAdsPositionRepository
import com.github.corentinc.core.ui.detectNewAds.DetectNewAdsPresenter
import com.github.corentinc.logger.NotifiCoinLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.jsoup.HttpStatusException
import java.util.concurrent.TimeUnit

class DetectNewAdsInteractor(
    private val searchAdsPositionRepository: SearchAdsPositionRepository,
    private val sharePreferencesRepository: SharedPreferencesRepository,
    private val globalSharedPreferencesRepository: GlobalSharedPreferencesRepository,
    private val detectNewAdsPresenter: DetectNewAdsPresenter,
    private val adComparator: Comparator<Ad>
) {
    companion object {
        private const val AD_MAXIMUM_PER_SEARCH = 200
    }

    fun onServiceStarted() {
        CoroutineScope(Dispatchers.IO).launch {
            var connexionErrorDuringAdCheck = false
            try {
                val remoteSearchAdsPositionList =
                    searchAdsPositionRepository.getRemoteSortedSearchAdsPosition()
                val localSearchAdsPositionList =
                    searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                val toSaveSearchAdsPositionList = mutableListOf<SearchAdsPosition>()
                remoteSearchAdsPositionList.forEach { remoteSearchAdsPosition ->
                    val localAdList = getCorrespondingLocalAdList(
                        remoteSearchAdsPosition,
                        localSearchAdsPositionList
                    )
                    var newAdList = getNewAds(remoteSearchAdsPosition, localAdList)
                    if(!sharePreferencesRepository.connexionErrorDuringLastAdCheck) {
                        newAdList = filterOlderAds(newAdList)
                    } else {
                        NotifiCoinLogger.i("connexion error during last ad check, no filtering on publicationDate")
                    }
                    if (newAdList.isNotEmpty()) {
                        logDifferences(localAdList, newAdList)
                        sendNotifications(remoteSearchAdsPosition, newAdList)
                    } else {
                        NotifiCoinLogger.i("AlarmManager didn't found new ads")
                    }
                    val allDistinctAds = (remoteSearchAdsPosition.ads + localAdList).distinct()
                    val adsToKeep = allDistinctAds.sortedWith(adComparator).take(AD_MAXIMUM_PER_SEARCH)
                    toSaveSearchAdsPositionList.add(SearchAdsPosition(remoteSearchAdsPosition.search, adsToKeep, remoteSearchAdsPosition.searchPosition))
                }
                searchAdsPositionRepository.replaceAll(toSaveSearchAdsPositionList)
            } catch (httpStatusException: HttpStatusException) {
                connexionErrorDuringAdCheck = true
                if (httpStatusException.statusCode == 403) {
                    NotifiCoinLogger.i("AlarmManager coundn't get new adds, forbidden 403")
                    detectNewAdsPresenter.presentErrorNotification(
                        AdListErrorType.FORBIDDEN,
                        httpStatusException
                    )
                } else {
                    NotifiCoinLogger.e("AlarmManager error when retrieving new ads : $httpStatusException")
                }
            } catch (exception: Exception) {
                connexionErrorDuringAdCheck = true
                NotifiCoinLogger.e("AlarmManager error when retrieving new ads : $exception")
            }
            detectNewAdsPresenter.stopSelf()
            sharePreferencesRepository.connexionErrorDuringLastAdCheck = connexionErrorDuringAdCheck
        }
    }

    private suspend fun logDifferences(localAds: List<Ad>, newAdList: List<Ad>) {
        val oldAds = newAdList.filter { oldAd ->
            localAds.any { newAd ->
                oldAd.title == newAd.title || oldAd.publicationDate == newAd.publicationDate || oldAd.url == newAd.url
            }
        }
        NotifiCoinLogger.i("OLD ADS : $oldAds")
        if(oldAds.isNotEmpty()) {
            withContext(Dispatchers.Main) {
                detectNewAdsPresenter.presentNewAdNotifications(
                    size = 1,
                    titlesString = "OLD $oldAds",
                    title ="OLD $oldAds",
                    url = "searchAdsPosition.search.url"
                )
            }
        }
        NotifiCoinLogger.i("NEW ADS : $newAdList")
    }

    private fun getNewAds(
        remoteSearchAdsPosition: SearchAdsPosition,
        localAds: List<Ad>
    ): List<Ad> {
        return remoteSearchAdsPosition.ads.filterNot {remoteAd ->
            localAds.any { localAd ->
                localAd.url == remoteAd.url
            }
        }
    }

    private fun getCorrespondingLocalAdList(
        remoteSearchAdsPosition: SearchAdsPosition,
        localSearchAdsPositionList: List<SearchAdsPosition>
    ): List<Ad> {
        return localSearchAdsPositionList.find { localSearchWithAds ->
            remoteSearchAdsPosition.search.id == localSearchWithAds.search.id
        }?.ads ?: emptyList()
    }

    private fun filterOlderAds(newAds: List<Ad>): List<Ad> {
        val now = DateTime.now().millis
        return newAds.filter {
            val publicationDate = it.publicationDate.millis
            val minutesDiff = TimeUnit.MILLISECONDS.toMinutes(now - publicationDate)
            if (minutesDiff > globalSharedPreferencesRepository.alarmIntervalPreference*2) {
                NotifiCoinLogger.i("THIS AD IS DETECTED TOO LATE AND NOT NOTIFIED : $it by $minutesDiff minutes")
            }
            minutesDiff < globalSharedPreferencesRepository.alarmIntervalPreference*2
        }
    }

    private suspend fun sendNotifications(searchAdsPosition: SearchAdsPosition, newAds: List<Ad>) {
        if (newAds.size == 1) {
            val titlesString: String = newAds[0].title
            NotifiCoinLogger.i(
                "AlarmManager found 1 new ad: $titlesString ${newAds[0].publicationDate.toString(
                    "HH:mm"
                )}"
            )
            withContext(Dispatchers.Main) {
                detectNewAdsPresenter.presentNewAdNotifications(
                    newAds.size,
                    titlesString,
                    searchAdsPosition.search.title,
                    newAds[0].url
                )
            }
        } else {
            val size = newAds.size
            val titlesString: String = newAds.joinToString(", ") { it.title }
            NotifiCoinLogger.i("AlarmManager found $size new ads : $titlesString, sending notifications")
            withContext(Dispatchers.Main) {
                detectNewAdsPresenter.presentNewAdNotifications(
                    size,
                    titlesString,
                    searchAdsPosition.search.title,
                    searchAdsPosition.search.url
                )
            }
        }
        val now = DateTime.now().millis
        newAds.forEach {
            val publicationDate = it.publicationDate.millis
            val minutesDiff = TimeUnit.MILLISECONDS.toMinutes(now - publicationDate)
            if (minutesDiff > 3) {
                NotifiCoinLogger.i("THIS AD IS DETECTED TOO LATE BUT STILL NOTIFIED: $it by $minutesDiff minutes")
                withContext(Dispatchers.Main) {
                    detectNewAdsPresenter.presentNewAdNotifications(
                        size = 1,
                        titlesString = "More than 3 minutes AND NOTIFIED : $minutesDiff minutes for ${it.title})",
                        title = searchAdsPosition.search.title,
                        url = searchAdsPosition.search.url
                    )
                }
            }
        }
    }
}