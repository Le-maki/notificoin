package com.github.corentinc.core.adList

import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.repository.searchAdsPosition.SearchAdsPositionRepository
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.logger.NotifiCoinLogger
import kotlinx.coroutines.*
import org.jsoup.HttpStatusException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class AdListInteractor(
    val adListPresenter: AdListPresenter,
    private val searchAdsPositionRepository: SearchAdsPositionRepository
) {
    private var refreshJob: Job? = null

    fun onRefresh(searchId: Int) {
        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                searchAdsPositionRepository.updateAllSearchAdsPositionFromWebPage()
                var searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                if (searchId != EditSearchInteractor.DEFAULT_ID) {
                    searchAdsPosition = searchAdsPosition.filter { it.search.id == searchId }
                }
                if (searchAdsPosition.all { it.ads.isEmpty() }) {
                    withContext(Dispatchers.Main) {
                        adListPresenter.presentEmptyList()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        with(adListPresenter) {
                            presentAdList(searchAdsPosition)
                            hideProgressBar()
                            presentErrorMessage(false)
                            presentAdsRecyclerView(true)
                            stopRefreshing()
                        }
                    }
                }
            } catch (error: Exception) {
                when (error) {
                    is UnknownHostException, is SocketTimeoutException -> {
                        NotifiCoinLogger.e("connection error getting ads:  $error ", error)
                        withContext(Dispatchers.Main) {
                            adListPresenter.presentConnectionError()
                        }
                    }
                    is ParseException -> {
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
                withContext(Dispatchers.Main) {
                    with(adListPresenter) {
                        hideProgressBar()
                        presentErrorMessage(true)
                        presentAdsRecyclerView(false)
                        stopRefreshing()
                    }
                }
            }
        }
    }

    fun stopRefresh() {
        runBlocking {
            refreshJob?.cancelAndJoin()
        }
    }
}