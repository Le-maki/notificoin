package com.github.lemaki.notificoin.domain.home

import com.github.lemaki.notificoin.data.repositories.AdRepository
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
	private val adRepository: AdRepository,
	private val homePresenter: HomePresenter
) {
	fun onStart() {
		CoroutineScope(Dispatchers.IO).launch {
			try {
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