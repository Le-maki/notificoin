package com.github.corentinc.core.home

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.SearchAdsPostionDefaultSorter
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchWithAds.SearchAdsPositionRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.home.HomePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeInteractor(
    val homePresenter: HomePresenter,
    private val searchRepository: SearchRepository,
    private val searchPositionRepository: SearchPositionRepository,
    private val searchAdsPositionRepository: SearchAdsPositionRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val searchAdsPostionDefaultSorter: SearchAdsPostionDefaultSorter,
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
            var searchWithAds = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
            if (searchWithAds.isEmpty()) {
                searches.forEach {
                    searchRepository.addSearch(Search(title = it.value, url = it.key))
                }
                searchWithAds = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
            }
            withContext(Dispatchers.Main) {
                searchWithAds = searchWithAds.sortedWith(searchAdsPostionDefaultSorter)
                homePresenter.presentSearches(searchWithAds.toMutableList())
            }
        }
    }

    fun onBatteryWhiteListAlertDialogNeutralButtonPressed() {
        sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
    }

    fun onCreateAdButtonPressed() {
        CoroutineScope(Dispatchers.IO).launch {
            val url = searches.toList()[0].first
            val title = searches.toList()[0].second
            val id = searchRepository.addSearch(Search(url = url, title = title))
            withContext(Dispatchers.Main) {
                homePresenter.presentEditSearchScreen(id.toInt(), url, title)
            }
        }
    }

    fun onSearchDeleted(searchAdsPosition: SearchAdsPosition) {
        CoroutineScope(Dispatchers.IO).launch {
            searchAdsPositionRepository.delete(searchAdsPosition.search)
        }
    }

    fun onStop(searchAdsPositionList: MutableList<SearchAdsPosition>) {
        CoroutineScope(Dispatchers.IO).launch {
            searchAdsPositionList.forEachIndexed { index, searchAdsPosition ->
                //searchPositionRepository.updateSearchPosition(searchAdsPosition.search.id, index)
            }
        }
    }
}