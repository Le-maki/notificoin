package com.github.corentinc.core.home

import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.SearchAdsPostionDefaultSorter
import com.github.corentinc.core.alarmManager.AlarmManagerInteractor
import com.github.corentinc.core.repository.GlobalSharedPreferencesRepository
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchAdsPosition.SearchAdsPositionRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.home.HomePresenter
import kotlinx.coroutines.*

class HomeInteractor(
    val homePresenter: HomePresenter,
    private val alarmManagerInteractor: AlarmManagerInteractor,
    private val searchRepository: SearchRepository,
    private val searchPositionRepository: SearchPositionRepository,
    private val searchAdsPositionRepository: SearchAdsPositionRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val globalSharedPreferencesRepository: GlobalSharedPreferencesRepository,
    private val searchAdsPostionDefaultSorter: SearchAdsPostionDefaultSorter
) {

    fun onStart(
        isBatteryWhiteListAlreadyGranted: Boolean,
        wasBatteryWhiteListDialogAlreadyShown: Boolean,
        shouldDisplaySpecialConstructorDialog: Boolean,
        id: Int,
        title: String,
        url: String
    ) {
        val shouldDisplayDefaultDialog = !isBatteryWhiteListAlreadyGranted
        if (sharedPreferencesRepository.shouldShowBatteryWhiteListDialog && !wasBatteryWhiteListDialogAlreadyShown && (shouldDisplaySpecialConstructorDialog || shouldDisplayDefaultDialog)) {
            homePresenter.presentBatteryWarningFragment(shouldDisplayDefaultDialog)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                var searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                searchAdsPosition = searchAdsPosition.sortedWith(searchAdsPostionDefaultSorter)
                withContext(Dispatchers.Main) {
                    if (searchAdsPosition.isEmpty()) {
                        homePresenter.presentEmptySearches()
                    } else {
                        homePresenter.presentSearches(searchAdsPosition.map { it.search }
                            .toMutableList())
                    }
                }
            }
            alarmManagerInteractor.updateAlarm(globalSharedPreferencesRepository.alarmIntervalPreference)
            if (id != EditSearchInteractor.DEFAULT_ID) {
                homePresenter.presentUndoDeleteSearch(Search(id, title, url))
            }
        }
    }

    fun onCreateAdButtonPressed() {
        homePresenter.presentEditSearchScreen()
    }

    fun onSearchDeleted(search: Search) {
        lateinit var searchAdsPositionList: List<SearchAdsPosition>
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                searchAdsPositionRepository.delete(search.id)
                searchAdsPositionList = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                searchAdsPositionList =
                    searchAdsPositionList.sortedWith(searchAdsPostionDefaultSorter)
            }.join()
            if (searchAdsPositionList.isEmpty()) {
                homePresenter.presentEmptySearches()
            }
        }
        homePresenter.presentUndoDeleteSearch(search)
    }

    fun beforeFragmentPause(searchList: MutableList<Search>) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                searchList.forEachIndexed { index, search ->
                    searchPositionRepository.updateSearchPosition(search.id, index)
                }
            }.join()
        }
    }

    fun onSearchClicked(search: Search) {
        homePresenter.presentAdListFragment(search)
    }

    fun onRestoreSearch(search: Search) {
        lateinit var searchAdsPosition: List<SearchAdsPosition>
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                searchRepository.addSearch(search)
                searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
                searchAdsPosition = searchAdsPosition.sortedWith(searchAdsPostionDefaultSorter)

            }.join()
        }
        homePresenter.presentSearches(searchAdsPosition.map { it.search }.toMutableList())
    }
}