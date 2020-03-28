package com.github.corentinc.notificoin.ui.home

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.ui.home.HomePresenter

class HomePresenterImpl(
    private val homeViewModel: HomeViewModel
): HomePresenter {
    lateinit var homeDisplay: HomeDisplay

    override fun presentBatteryWhitelistPermissionAlertDialog() {
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = true
    }

    override fun presentSearches(searchAdsPosition: MutableList<SearchAdsPosition>) {
        homeViewModel.searchAdsPositionList.value = searchAdsPosition
    }

    override fun presentEditSearchScreen(id: Int, url: String, title: String) {
        homeDisplay.displayEditAdScreen(id, url, title)
    }
}