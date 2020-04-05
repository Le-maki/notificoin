package com.github.corentinc.notificoin.ui.adList

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.notificoin.ui.ad.AdListToAdViewModelListTransformer

class AdListPresenterImpl(
    private val adsListToAdViewModelListTransformer: AdListToAdViewModelListTransformer,
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

    override fun presentAdList(searchAdsPositionList: List<SearchAdsPosition>) {
        adListViewModel.adViewModelList.value =
            adsListToAdViewModelListTransformer.transform(searchAdsPositionList)
    }

}