package com.github.corentinc.core.adList

import com.github.corentinc.core.repository.searchWithAds.SearchAdsPositionRepository
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.logger.NotifiCoinLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class AdListInteractor(
    private val adListPresenter: AdListPresenter,
    private val searchAdsPositionRepository: SearchAdsPositionRepository
) {

    fun onStart(searchId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                searchAdsPositionRepository.updateAllSearchAdsPositionFromWebPage()
                var searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                if (searchId != -1) {
                    searchAdsPosition = searchAdsPosition.filter { it.search.id == searchId }
                }
                withContext(Dispatchers.Main) {
                    adListPresenter.presentAdList(searchAdsPosition)
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
                    is HttpStatusException -> {
                        if (error.statusCode == 403) {
                            NotifiCoinLogger.e("error getting ads:  $error ", error)
                            withContext(Dispatchers.Main) {
                                adListPresenter.presentForbiddenError()
                            }
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