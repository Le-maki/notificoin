package com.github.corentinc.notificoin.ui.home

import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.home.HomePresenter

class HomePresenterImpl : HomePresenter {
    lateinit var homeDisplay: HomeDisplay

    override fun presentSearches(search: MutableList<Search>) {
        homeDisplay.displaySearches(search)
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

    override fun presentBatteryWarningFragment(
        shouldDisplayDefaultDialog: Boolean
    ) {
        homeDisplay.displayBatteryWarningFragment(shouldDisplayDefaultDialog)
    }

    override fun presentEmptySearches() {
        homeDisplay.displayEmptySearches()
    }

    override fun presentProgressBar() {
        homeDisplay.displayProgressBar()
    }
}