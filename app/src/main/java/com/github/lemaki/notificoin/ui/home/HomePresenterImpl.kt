package com.github.lemaki.notificoin.ui.home

import com.github.lemaki.core.ad.Ad
import com.github.lemaki.core.home.HomeErrorType
import com.github.lemaki.core.search.Search
import com.github.lemaki.core.ui.home.HomePresenter
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer

class HomePresenterImpl(
    private val adsListViewModelTransformer: AdListToAdsListViewModelTransformer,
    private val homeViewModel: HomeViewModel
): HomePresenter {
    private fun presentError(errorType: HomeErrorType) {
        homeViewModel.errorType.value = errorType
    }

    override fun presentConnectionError() {
        presentError(HomeErrorType.CONNECTION)
    }

    override fun presentParsingError() {
        presentError(HomeErrorType.PARSING)
    }

    override fun presentUnknownError() {
        presentError(HomeErrorType.UNKNOWN)
    }

    override fun presentAdList(adList: List<Ad>) {
        homeViewModel.adListViewModel.value = adsListViewModelTransformer.transform(adList)
    }

    override fun presentBatteryWhitelistPermissionAlertDialog() {
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = true
    }

    override fun presentSearches(searchList: List<Search>) {
        homeViewModel.searchList.value = searchList
    }
}