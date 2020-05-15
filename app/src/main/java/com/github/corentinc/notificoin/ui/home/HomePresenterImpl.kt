package com.github.corentinc.notificoin.ui.home

import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.home.HomePresenter

class HomePresenterImpl(
    private val homeViewModel: HomeViewModel
): HomePresenter {
    lateinit var homeDisplay: HomeDisplay

    override fun presentSearches(search: MutableList<Search>) {
        homeViewModel.searchAdsPositionList.value = search
    }

    override fun presentEditSearchScreen(id: Int?, url: String?, title: String?) {
        homeDisplay.displayEditSearchScreen(id, url, title)
    }

    override fun presentAdListFragment(search: Search) {
        homeDisplay.displayAdListScreen(search.id)
    }

    override fun presentUndoDeleteSearch(search: Search) {
        homeDisplay.displayUndoDeleteSearch(search)
    }

    override fun presentBatteryWarningFragment() {
        homeDisplay.displayBatteryWarningFragment()
    }
}