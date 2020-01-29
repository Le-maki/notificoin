package com.github.lemaki.notificoin.domain.home

import com.github.lemaki.notificoin.data.ad.AdRepository
import com.github.lemaki.notificoin.data.search.SearchRepository
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
	private val searchRepository: SearchRepository,
	private val adRepository: AdRepository,
	private val homePresenter: HomePresenter,
	private val searches: Map<String, String> = mapOf(
		"https://www.leboncoin.fr/recherche/?text=mario%20maker%202&locations=Nantes" to "Mario Maker",
		"https://www.leboncoin.fr/recherche/?category=2&text=voiture&locations=Nantes" to "Voiture"
	)
) {
	fun onStart() {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				searches.forEach { searchRepository.addSearch(Search(it.key, it.value)) }
				adRepository.updateAdsFromWebPage(searches.keys.elementAt((0..1).random()))
				val ads = adRepository.getAds()
				withContext(Dispatchers.Main) {
					homePresenter.presentAdList(ads)
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