package com.github.corentinc.core.home

import com.github.corentinc.core.SearchAdsPostionDefaultSorter
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchWithAds.SearchAdsPositionRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.home.HomePresenter
import kotlinx.coroutines.*

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
    private var deletedSearchList: MutableList<Search> = mutableListOf()

    fun onStart(
        isBatteryWhiteListAlreadyGranted: Boolean,
        wasBatteryWhiteListDialogAlreadyShown: Boolean
    ) {
        deletedSearchList = mutableListOf()
        if (sharedPreferencesRepository.shouldShowBatteryWhiteListDialog && !isBatteryWhiteListAlreadyGranted && !wasBatteryWhiteListDialogAlreadyShown
        ) {
            homePresenter.presentBatteryWhitelistPermissionAlertDialog()
        }
        CoroutineScope(Dispatchers.IO).launch {
            var searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
            if (searchAdsPosition.isEmpty()) {
                searches.forEach {
                    searchRepository.addSearch(Search(title = it.value, url = it.key))
                }
                searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
            }
            withContext(Dispatchers.Main) {
                searchAdsPosition = searchAdsPosition.sortedWith(searchAdsPostionDefaultSorter)
                homePresenter.presentSearches(searchAdsPosition.map { it.search }.toMutableList())
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

    fun onSearchDeleted(search: Search) {
        deletedSearchList.add(search)
        homePresenter.presentUndoDeleteSearch(search)
    }

    fun beforeFragmentPause(searchList: MutableList<Search>) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                deletedSearchList.forEach { search ->
                    searchAdsPositionRepository.delete(search.id)
                }
                deletedSearchList = mutableListOf()
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            searchList.forEachIndexed { index, search ->
                searchPositionRepository.updateSearchPosition(search.id, index)
            }
        }
    }

    fun onSearchClicked(search: Search) {
        homePresenter.presentAdListFragment(search)
    }

    fun onRestoreSearch(search: Search) {
        deletedSearchList.remove(search)
    }
}