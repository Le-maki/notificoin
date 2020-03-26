package com.github.lemaki.notificoin.ui.home

import com.github.lemaki.core.search.Search
import com.github.lemaki.core.ui.home.HomePresenter

class HomePresenterImpl(
    private val homeViewModel: HomeViewModel
): HomePresenter {
    lateinit var homeDisplay: HomeDisplay

    override fun presentBatteryWhitelistPermissionAlertDialog() {
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = true
    }

    override fun presentSearches(searchList: MutableList<Search>) {
        if (homeViewModel.searchList.value != searchList) {
            homeViewModel.searchList.value = searchList
        }
    }

    override fun presentEditSearchScreen(id: Int, url: String, title: String) {
        homeDisplay.displayEditAdScreen(id, url, title)
    }
}