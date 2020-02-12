package com.github.lemaki.notificoin.ui.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.notificoin.domain.SearchWithAds
import com.github.lemaki.notificoin.domain.ad.Ad
import com.github.lemaki.notificoin.injection.adModule
import com.github.lemaki.notificoin.injection.databaseModule
import com.github.lemaki.notificoin.injection.searchModule
import com.github.lemaki.notificoin.injection.searchWithAdsModule
import com.github.lemaki.notificoin.injection.webPageModule
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.inject

class AlarmBroadcastReceiver: BroadcastReceiver(), KoinComponent {
    private val searchWithAdsRepository: SearchWithAdsRepository by inject()

    override fun onReceive(context: Context, intent: Intent) {
        NotifiCoinLogger.i(context.getString(R.string.enteringOnReceive))
        try {
            startKoin {
                androidLogger()
                fragmentFactory()
                androidContext(context)
                modules(
                    listOf(
                        databaseModule,
                        adModule,
                        searchModule,
                        webPageModule,
                        searchWithAdsModule
                    )
                )
            }
        } catch (exception: KoinAppAlreadyStartedException) {
            NotifiCoinLogger.i(context.getString(R.string.koin_already_started))
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteSearchWithAdsList = searchWithAdsRepository.getRemoteSortedSearchWithAds()
                val localSearchWithAdsList = searchWithAdsRepository.getAllSortedSearchWithAds()
                if (remoteSearchWithAdsList == localSearchWithAdsList) {
                    NotifiCoinLogger.i(context.getString(R.string.noNewAds))
                } else {
                    remoteSearchWithAdsList.forEach { remoteSearchWithAds ->
                        val newAds = getNewAds(remoteSearchWithAds, localSearchWithAdsList)
                        if (newAds.isNotEmpty()) {
                            sendNewAdNotifications(remoteSearchWithAds, newAds, context)
                        } else {
                            NotifiCoinLogger.i(context.getString(R.string.noNewAds) + context.getString(R.string.someAdsDeleted))
                        }
                    }
                }
                searchWithAdsRepository.replaceAll(remoteSearchWithAdsList)
            } catch (exception: Exception) {
                NotifiCoinLogger.e(context.getString(R.string.errorInAlarmManager), exception)
            }
        }
    }

    private suspend fun sendNewAdNotifications(searchWithAds: SearchWithAds, newAds: List<Ad>, context: Context) {
        if (newAds.size == 1) {
            val titlesString: String = newAds[0].title.take(15)
            NotifiCoinLogger.i(context.resources.getQuantityString(R.plurals.newAdNotificationLog, 1, titlesString + newAds[0].publicationDate.toString("HH:mm")))
            withContext(Dispatchers.Main) {
                NotificationManager(context).sendNotification(
                    context.getString(R.string.newAdNotificationTitle),
                    context.resources.getQuantityString(R.plurals.newAdNotificationText, newAds.size, searchWithAds.search.title, titlesString)
                )
            }
        } else {
            val size = newAds.size
            val titlesString: String = newAds.joinToString(", ") { it.title }
            NotifiCoinLogger.i(context.resources.getQuantityString(R.plurals.newAdNotificationLog, size, size, titlesString))
            withContext(Dispatchers.Main) {
                NotificationManager(context).sendBigtextNotification(
                    context.getString(R.string.newAdNotificationTitle),
                    context.resources.getQuantityString(R.plurals.newAdNotificationText, size, size, searchWithAds.search.title),
                    context.getString(R.string.newAdNotificationBigText, titlesString)
                )
            }
        }
    }

    private fun getNewAds(remoteSearchWithAds: SearchWithAds, localSearchWithAdsList: List<SearchWithAds>): List<Ad> {
        return remoteSearchWithAds.ads.minus(
            localSearchWithAdsList.find { localSearchWithAds ->
                remoteSearchWithAds.search.url == localSearchWithAds.search.url
            }?.ads ?: emptyList()
        )
    }
}