package com.github.corentinc.core.home

import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.SearchAdsPostionDefaultSorter
import com.github.corentinc.core.alarmManager.AlarmManagerInteractor
import com.github.corentinc.core.repository.GlobalSharedPreferencesRepository
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchAdsPosition.SearchAdsPositionRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.home.HomePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        id: Int,
        title: String,
        url: String
    ) {
        if (sharedPreferencesRepository.shouldShowBatteryWhiteListDialog && !isBatteryWhiteListAlreadyGranted && !wasBatteryWhiteListDialogAlreadyShown
        ) {
            homePresenter.presentBatteryWarningFragment()
        }
        CoroutineScope(Dispatchers.IO).launch {
            var searchAdsPosition = searchAdsPositionRepository.getAllSortedSearchAdsPosition()
            withContext(Dispatchers.Main) {
                searchAdsPosition = searchAdsPosition.sortedWith(searchAdsPostionDefaultSorter)
                homePresenter.presentSearches(searchAdsPosition.map { it.search }.toMutableList())
            }
        }
        alarmManagerInteractor.updateAlarm(globalSharedPreferencesRepository.alarmIntervalPreference)
        if (id != EditSearchInteractor.DEFAULT_ID) {
            homePresenter.presentUndoDeleteSearch(Search(id, title, url))
        }
    }

    fun onCreateAdButtonPressed() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                homePresenter.presentEditSearchScreen()
            }
        }
    }

    fun onSearchDeleted(search: Search) {
        CoroutineScope(Dispatchers.IO).launch {
            searchAdsPositionRepository.delete(search.id)
        }
        homePresenter.presentUndoDeleteSearch(search)
    }

    fun beforeFragmentPause(searchList: MutableList<Search>) {
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
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.addSearch(search)
        }
    }
}