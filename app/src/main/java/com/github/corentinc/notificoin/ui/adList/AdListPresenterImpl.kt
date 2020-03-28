package com.github.corentinc.notificoin.ui.adList

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.notificoin.ui.ad.AdListToAdsListViewModelTransformer

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
        adListViewModel.adListTextViewModel.value = adsListViewModelTransformer.transform(adList)
    }

}