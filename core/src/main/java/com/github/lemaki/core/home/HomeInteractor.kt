package com.github.lemaki.core.home

import com.github.lemaki.core.repository.SharedPreferencesRepository
import com.github.lemaki.core.repository.search.SearchRepository
import com.github.lemaki.core.repository.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.core.search.Search
import com.github.lemaki.core.ui.home.HomePresenter
import com.github.lemaki.logger.NotifiCoinLogger
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
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val searches: Map<String, String> = mapOf(
        "https://www.leboncoin.fr/recherche/?category=2&locations=Nantes&regdate=2010-max" to "Voiture",
        "https://www.leboncoin.fr/recherche/?text=jeu%20switch&locations=Nantes__47.23898554566441_-1.5262136157260586_10000" to "Jeu Switch"
    )
) {
    private var batteryPermissionWasAskedOnce = false

    fun onStart(isBatteryWhiteListAlreadyGranted: Boolean) {
        if (sharedPreferencesRepository.shouldShowBatteryWhiteListDialog && !isBatteryWhiteListAlreadyGranted && !batteryPermissionWasAskedOnce
        ) {
            homePresenter.presentBatteryWhitelistPermissionAlertDialog()
            batteryPermissionWasAskedOnce = true
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                searches.forEach {
                    searchRepository.addSearch(Search(it.key, it.value))
                }
                searchWithAdsRepository.updateAllSearchWithAds()
                val searchWithAds = searchWithAdsRepository.getAllSortedSearchWithAds()
                withContext(Dispatchers.Main) {
                    homePresenter.presentAdList(searchWithAds.map { it.ads }.flatten())
                    homePresenter.presentSearches(searchWithAds.map { it.search })
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

    fun onBatteryWhiteListAlertDialogNeutralButtonPressed() {
        sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
    }
}