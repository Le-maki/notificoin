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
import org.joda.time.DateTime
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
        NotifiCoinLogger.i("Entering OnReceive in AlarmManager")
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
                writeLastUpdate(context)
                val remoteSearchWithAdsList = searchWithAdsRepository.getRemoteSearchWithAds()
                val localSearchWithAdsList = searchWithAdsRepository.getSearchWithAds()
                if (remoteSearchWithAdsList == localSearchWithAdsList) {
                    NotifiCoinLogger.i("AlarmManager didn't found new ads")
                } else {
                    remoteSearchWithAdsList.forEach { remoteSearchWithAds ->
                        val newAds = remoteSearchWithAds.ads.minus(
                            localSearchWithAdsList.find { localSearchWithAds ->
                                remoteSearchWithAds.search.url == localSearchWithAds.search.url
                            }?.ads ?: run {
                                // TODO
                                throw Exception()
                            }
                        )
                        if (newAds.isNotEmpty()) {
                            sendNewAdNotifications(remoteSearchWithAds, newAds, context)
                        } else {
                            NotifiCoinLogger.i("AlarmManager didn't found new ads, some were deleted")
                        }
                    }
                }
                searchWithAdsRepository.replaceAll(remoteSearchWithAdsList)
            } catch (exception: Exception) {
                NotifiCoinLogger.e("EXCEPTION", exception)
            }
        }
    }

    private fun writeLastUpdate(context: Context) {
        val sharedPref = context.getSharedPreferences("Notificoin", Context.MODE_PRIVATE)
        val oldUpdateTimeString = ""
        sharedPref.getString("lastUpdate", oldUpdateTimeString)
        with(sharedPref.edit()) {
            putString("lastUpdate", DateTime.now().toString("HH:mm"))
            commit()
        }
        if (oldUpdateTimeString.isNotEmpty()) {
            NotifiCoinLogger.i("last update: $oldUpdateTimeString, now: ${DateTime.now().toString("HH:mm")}")
        }
    }

    private suspend fun sendNewAdNotifications(searchWithAds: SearchWithAds, newAds: List<Ad>, context: Context) {
        if (newAds.size == 1) {
            NotifiCoinLogger.i("AlarmManager found 1 new ad ${newAds[0].title.take(15)} ${newAds[0].publicationDate.toString("HH:mm")}, sending notification")
            withContext(Dispatchers.Main) {
                NotificationManager(context).sendNotification(
                    "New Ad !",
                    context.resources.getQuantityString(R.plurals.newAdsFoundMessage, newAds.size, searchWithAds.search.title, newAds[0].title.take(15))
                )
            }
        } else {
            NotifiCoinLogger.i("AlarmManager found ${newAds.size} new ads : ${newAds.map { it.title }.joinToString { "," }}, sending notifications")
            withContext(Dispatchers.Main) {
                NotificationManager(context).sendBigtextNotification(
                    "New Ads !",
                    context.resources.getQuantityString(R.plurals.newAdsFoundMessage, newAds.size, newAds.size, searchWithAds.search.title),
                    "Ads found : ${newAds.map { it.title }.joinToString { "," }}"
                )
            }
        }
    }
}