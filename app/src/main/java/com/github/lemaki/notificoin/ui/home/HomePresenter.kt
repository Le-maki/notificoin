package com.github.lemaki.notificoin.ui.home

import com.github.lemaki.notificoin.domain.ad.Ad
import com.github.lemaki.notificoin.domain.home.HomeErrorType
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer

class HomePresenter(
	private val adsListViewModelTransformer: AdListToAdsListViewModelTransformer,
	private val homeViewModel: HomeViewModel
) {
	private fun presentError(errorType: HomeErrorType) {
		homeViewModel.errorType.value = errorType
	}

	fun presentConnectionError() {
		presentError(HomeErrorType.CONNECTION)
	}

	fun presentParsingError() {
		presentError(HomeErrorType.PARSING)
	}

	fun presentUnknownError() {
		presentError(HomeErrorType.UNKNOWN)
	}

	fun presentAdList(adList: List<Ad>) {
		homeViewModel.adListViewModel.value = adsListViewModelTransformer.transform(adList)
	}

	fun presentBatteryWhitelistPermissionAlertDialog() {
		homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = true
	}
}