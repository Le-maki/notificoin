package com.github.lemaki.notificoin.ui.adList

import com.github.lemaki.core.ad.Ad
import com.github.lemaki.core.adList.AdListErrorType
import com.github.lemaki.core.ui.adList.AdListPresenter
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer

class AdListPresenterImpl(
    private val adsListViewModelTransformer: AdListToAdsListViewModelTransformer,
    private val adListViewModel: AdListViewModel
): AdListPresenter {
    private fun presentError(errorType: AdListErrorType) {
        adListViewModel.errorType.value = errorType
    }

    override fun presentConnectionError() {
        presentError(AdListErrorType.CONNECTION)
    }

    override fun presentParsingError() {
        presentError(AdListErrorType.PARSING)
    }

    override fun presentUnknownError() {
        presentError(AdListErrorType.UNKNOWN)
    }

    override fun presentAdList(adList: List<Ad>) {
        adListViewModel.adListViewModel.value = adsListViewModelTransformer.transform(adList)
    }

}