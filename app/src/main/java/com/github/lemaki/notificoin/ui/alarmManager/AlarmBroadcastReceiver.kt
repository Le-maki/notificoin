package com.github.lemaki.notificoin.ui.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.github.lemaki.notificoin.data.NotifiCoinDataBase
import com.github.lemaki.notificoin.data.ad.AdDataSource
import com.github.lemaki.notificoin.data.ad.AdRepository
import com.github.lemaki.notificoin.data.search.SearchDataSource
import com.github.lemaki.notificoin.data.search.SearchRepository
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsDataSource
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.notificoin.data.webpage.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.data.webpage.WebPageDataSource
import com.github.lemaki.notificoin.data.webpage.WebPageRepository
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class AlarmBroadcastReceiver: BroadcastReceiver(), KoinComponent {

	override fun onReceive(context: Context, intent: Intent) {
		val dataBase = Room.databaseBuilder<NotifiCoinDataBase>(context, NotifiCoinDataBase::class.java, "ad.db").build()
		val searchWithAdsRepository = SearchWithAdsRepository(
			SearchWithAdsDataSource(dataBase.searchWithAdsDao()),
			SearchRepository(SearchDataSource(dataBase.searchDao())),
			AdRepository(WebPageRepository(WebPageDataSource()), DocumentToAdJsonArrayTransformer(), AdDataSource(dataBase.adDao()))
		)
		CoroutineScope(Dispatchers.IO).launch {
			val oldSearchWithAdsList = searchWithAdsRepository.getSearchWithAds()
			searchWithAdsRepository.updateAllSearchWithAds()
			val newSearchWithAdsList = searchWithAdsRepository.getSearchWithAds()
			newSearchWithAdsList.forEach { newSearchWithAds ->
				if (newSearchWithAds.ads.any { ad ->
						oldSearchWithAdsList.find {
							it.search.url == newSearchWithAds.search.url
						}?.ads?.contains(ad) != true
					}) {
					withContext(Dispatchers.Main) {
						NotificationManager(context).sendNotification("New Ad !", "New ad for your search :\"${newSearchWithAds.search.title}\"")
					}
				}
			}
		}
	}

}