package com.github.lemaki.notificoin.domain

import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.notificoin.domain.ad.Ad
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.ui.detectNewAds.DetectNewAdsPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

class DetectNewAdsInteractor(
    private val searchWithAdsRepository: SearchWithAdsRepository,
    private val detectNewAdsPresenter: DetectNewAdsPresenter
) {

    fun onServiceStarted() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteSearchWithAdsList = searchWithAdsRepository.getRemoteSortedSearchWithAds()
                val localSearchWithAdsList = searchWithAdsRepository.getAllSortedSearchWithAds()
                if (remoteSearchWithAdsList == localSearchWithAdsList) {
                    NotifiCoinLogger.i("AlarmManager didn't found new ads")
                } else {
                    remoteSearchWithAdsList.forEach { remoteSearchWithAds ->
                        val newAds = getNewAds(remoteSearchWithAds, localSearchWithAdsList)
                        if (newAds.isNotEmpty()) {
                            sendNotifications(remoteSearchWithAds, newAds)
                        } else {
                            NotifiCoinLogger.i("AlarmManager didn't found new ads, some were deleted")
                        }
                    }
                }
                searchWithAdsRepository.replaceAll(remoteSearchWithAdsList)
            } catch (exception: Exception) {
                NotifiCoinLogger.e("Alarm Manager couldn't check new ads or send notifications $exception")
                detectNewAdsPresenter.presentBigtextNotification(
                    "Oops",
                    "Error at ${DateTime.now().toString("HH:mm")}, you were offline maybe ?",
                    exception.toString()
                )
            }
            detectNewAdsPresenter.stopSelf()
        }
    }

    private fun getNewAds(remoteSearchWithAds: SearchWithAds, localSearchWithAdsList: List<SearchWithAds>): List<Ad> {
        return remoteSearchWithAds.ads.minus(
            localSearchWithAdsList.find { localSearchWithAds ->
                remoteSearchWithAds.search.url == localSearchWithAds.search.url
            }?.ads ?: emptyList()
        )
    }

    private suspend fun sendNotifications(searchWithAds: SearchWithAds, newAds: List<Ad>) {
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
                    searchWithAds.search.title,
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
                    searchWithAds.search.title,
                    searchWithAds.search.url
                )
            }
        }
    }
}