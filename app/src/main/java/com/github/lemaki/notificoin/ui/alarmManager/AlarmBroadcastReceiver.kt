package com.github.lemaki.notificoin.ui.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsRepository
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
				val oldSearchWithAdsList = searchWithAdsRepository.getSearchWithAds()
				searchWithAdsRepository.updateAllSearchWithAds()
				val newSearchWithAdsList = searchWithAdsRepository.getSearchWithAds()
				newSearchWithAdsList.forEach { newSearchWithAds ->
					if (newSearchWithAds.ads.any { ad ->
							oldSearchWithAdsList.find {
								it.search.url == newSearchWithAds.search.url
							}?.ads?.contains(ad) != true
						}) {
						NotifiCoinLogger.i("AlarmManager found new ads, sending notifications")
						withContext(Dispatchers.Main) {
							NotificationManager(context).sendNotification("New Ad !", "New ad for your search : \"${newSearchWithAds.search.title}\"")
						}
					} else {
						NotifiCoinLogger.i("AlarmManager didn't found new ads")
					}
				}
			} catch (exception: Exception) {
				NotifiCoinLogger.e("EXCEPTION", exception)
			}
		}
	}

}