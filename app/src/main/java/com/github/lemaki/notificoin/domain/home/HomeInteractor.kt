package com.github.lemaki.notificoin.domain.home

import com.github.lemaki.notificoin.data.search.SearchRepository
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.notificoin.domain.search.Search
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.ui.home.HomePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class HomeInteractor(
	private val homePresenter: HomePresenter,
	private val searchRepository: SearchRepository,
	private val searchWithAdsRepository: SearchWithAdsRepository,
	private val searches: Map<String, String> = mapOf(
		"https://www.leboncoin.fr/recherche/?category=2&locations=Nantes&regdate=2010-max" to "Voiture"
	)

) {
	fun onStart() {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				searches.forEach {
					searchRepository.addSearch(Search(it.key, it.value))
				}
				searchWithAdsRepository.updateAllSearchWithAds()
                val searchWithAds = searchWithAdsRepository.getAllSortedSearchWithAds()
				withContext(Dispatchers.Main) {
					homePresenter.presentAdList(searchWithAds.map { it.ads }.flatten())
				}
			} catch (error: Exception) {
				when (error) {
					is UnknownHostException, is SocketTimeoutException -> {
						NotifiCoinLogger.e("connection error getting ads:  $error ", error)
						withContext(Dispatchers.Main) {
							homePresenter.presentConnectionError()
						}
					}
					is ParseException, is IllegalStateException -> {
						NotifiCoinLogger.e("error parsing ads:  $error ", error)
						withContext(Dispatchers.Main) {
							homePresenter.presentParsingError()
						}
					}
					else -> {
						NotifiCoinLogger.e("unknown error:  $error ", error)
						withContext(Dispatchers.Main) {
							homePresenter.presentUnknownError()
						}
					}
				}
			}
		}
	}
}