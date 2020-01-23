package com.github.lemaki.notificoin.ui.home

import com.github.lemaki.notificoin.domain.ad.Ad
import com.github.lemaki.notificoin.domain.home.HomeErrorType
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer

class HomePresenter(private val display: HomeDisplay, private val adsListViewModelTransformer: AdListToAdsListViewModelTransformer) {
	private fun presentError(errorType: HomeErrorType) {
		display.displayError(HomeViewModel(errorType = errorType))
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
		display.displayAdList(
			HomeViewModel(
				adListViewModel = adsListViewModelTransformer.transform(adList))
		)
	}
}