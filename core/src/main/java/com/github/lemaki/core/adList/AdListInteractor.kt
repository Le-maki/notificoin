package com.github.lemaki.core.adList

import com.github.lemaki.core.repository.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.core.ui.adList.AdListPresenter
import com.github.lemaki.logger.NotifiCoinLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class AdListInteractor(
    private val adListPresenter: AdListPresenter,
    private val searchWithAdsRepository: SearchWithAdsRepository
) {

    fun onStart() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                searchWithAdsRepository.updateAllSearchWithAds()
                val searchWithAds = searchWithAdsRepository.getAllSortedSearchWithAds()
                withContext(Dispatchers.Main) {
                    adListPresenter.presentAdList(searchWithAds.map { it.ads }.flatten())
                }
            } catch (error: Exception) {
                when (error) {
                    is UnknownHostException, is SocketTimeoutException -> {
                        NotifiCoinLogger.e("connection error getting ads:  $error ", error)
                        withContext(Dispatchers.Main) {
                            adListPresenter.presentConnectionError()
                        }
                    }
                    is ParseException, is IllegalStateException -> {
                        NotifiCoinLogger.e("error parsing ads:  $error ", error)
                        withContext(Dispatchers.Main) {
                            adListPresenter.presentParsingError()
                        }
                    }
                    else -> {
                        NotifiCoinLogger.e("unknown error:  $error ", error)
                        withContext(Dispatchers.Main) {
                            adListPresenter.presentUnknownError()
                        }
                    }
                }
            }
        }
    }
}