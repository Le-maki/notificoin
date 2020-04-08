package com.github.corentinc.core

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.repository.searchWithAds.SearchAdsPositionRepository
import com.github.corentinc.core.ui.detectNewAds.DetectNewAdsPresenter
import com.github.corentinc.logger.NotifiCoinLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException

class DetectNewAdsInteractor(
    private val searchAdsPositionRepository: SearchAdsPositionRepository,
    private val detectNewAdsPresenter: DetectNewAdsPresenter
) {

    fun onServiceStarted() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteSearchAdsPositionList =
                    searchAdsPositionRepository.getRemoteSortedSearchAdsPosition()
                val localSearchAdsPositionList =
                    searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                if (remoteSearchAdsPositionList == localSearchAdsPositionList) {
                    NotifiCoinLogger.i("AlarmManager didn't found new ads")
                } else {
                    remoteSearchAdsPositionList.forEach { remoteSearchWithAds ->
                        val newAds = getNewAds(remoteSearchWithAds, localSearchAdsPositionList)
                        if (newAds.isNotEmpty()) {
                            sendNotifications(remoteSearchWithAds, newAds)
                        } else {
                            NotifiCoinLogger.i("AlarmManager didn't found new ads, some were deleted")
                        }
                    }
                }
                searchAdsPositionRepository.replaceAll(remoteSearchAdsPositionList)
            } catch (httpStatusException: HttpStatusException) {
                if (httpStatusException.statusCode == 403) {
                    NotifiCoinLogger.i("AlarmManager coundn't get new adds, forbidden 403")
                    detectNewAdsPresenter.presentErrorNotification(
                        AdListErrorType.FORBIDDEN,
                        httpStatusException
                    )
                } else {
                    sendUnknowErrorNotification(httpStatusException)
                }
            } catch (exception: Exception) {
                sendUnknowErrorNotification(exception)
            }
            detectNewAdsPresenter.stopSelf()
        }
    }

    private fun getNewAds(
        remoteSearchAdsPosition: SearchAdsPosition,
        localSearchAdsPositionList: List<SearchAdsPosition>
    ): List<Ad> {
        return remoteSearchAdsPosition.ads.minus(
            localSearchAdsPositionList.find { localSearchWithAds ->
                remoteSearchAdsPosition.search.id == localSearchWithAds.search.id
            }?.ads ?: emptyList()
        )
    }

    private suspend fun sendNotifications(searchAdsPosition: SearchAdsPosition, newAds: List<Ad>) {
        if (newAds.size == 1) {
            val titlesString: String = newAds[0].title.take(15)
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
    }

    private fun sendUnknowErrorNotification(exception: Exception) {
        NotifiCoinLogger.e("Alarm Manager couldn't check new ads or send notifications $exception")
        detectNewAdsPresenter.presentErrorNotification(
            AdListErrorType.UNKNOWN,
            exception
        )
    }
}