package com.github.lemaki.notificoin.ui.home

import com.github.lemaki.core.search.Search
import com.github.lemaki.core.ui.home.HomePresenter

class HomePresenterImpl(
    private val homeViewModel: HomeViewModel
): HomePresenter {
    override fun presentBatteryWhitelistPermissionAlertDialog() {
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = true
    }

    override fun presentSearches(searchList: List<Search>) {
        homeViewModel.searchList.value = searchList
    }
}