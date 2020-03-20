package com.github.lemaki.core.home

import com.github.lemaki.core.repository.SharedPreferencesRepository
import com.github.lemaki.core.repository.search.SearchRepository
import com.github.lemaki.core.repository.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.core.search.Search
import com.github.lemaki.core.ui.home.HomePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun onStart(
        isBatteryWhiteListAlreadyGranted: Boolean,
        wasBatteryWhiteListDialogAlreadyShown: Boolean
    ) {
        if (sharedPreferencesRepository.shouldShowBatteryWhiteListDialog && !isBatteryWhiteListAlreadyGranted && !wasBatteryWhiteListDialogAlreadyShown
        ) {
            homePresenter.presentBatteryWhitelistPermissionAlertDialog()
        }
        CoroutineScope(Dispatchers.IO).launch {
                searches.forEach {
                    searchRepository.addSearch(Search(it.key, it.value))
                }
                val searchWithAds = searchWithAdsRepository.getAllSortedSearchWithAds()
                withContext(Dispatchers.Main) {
                    homePresenter.presentSearches(searchWithAds.map { it.search })
                }
        }
    }

    fun onBatteryWhiteListAlertDialogNeutralButtonPressed() {
        sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
    }
}