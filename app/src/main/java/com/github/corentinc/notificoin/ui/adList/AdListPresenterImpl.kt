package com.github.corentinc.notificoin.ui.adList

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.ui.adList.AdListDisplay
import com.github.corentinc.core.ui.adList.AdListPresenter

class AdListPresenterImpl(
    private val adsListToAdViewModelListTransformer: AdListToAdViewModelListTransformer
) : AdListPresenter {
    lateinit var adListDisplay: AdListDisplay

    override fun presentConnectionError() {
        adListDisplay.displayConnectionError()
    }

    override fun presentParsingError() {
        adListDisplay.displayParsingError()
    }

    override fun presentUnknownError() {
        adListDisplay.displayUnknownError()
    }

    override fun presentAdList(searchAdsPositionList: List<SearchAdsPosition>) {
        adListDisplay.displayAdList(
            adsListToAdViewModelListTransformer.transform(
                searchAdsPositionList
            )
        )
    }

    override fun presentForbiddenError() {
        adListDisplay.displayForbiddenError()
    }

    override fun presentEmptyList() {
        adListDisplay.displayEmptyList()
    }

    override fun hideProgressBar() {
        adListDisplay.hideProgressBar()
    }

    override fun presentErrorMessage(isVisible: Boolean) {
        adListDisplay.displayErrorMessage(isVisible)
    }

    override fun presentAdsRecyclerView(isVisible: Boolean) {
        adListDisplay.displayAdsRecyclerView(isVisible)
    }

    override fun stopRefreshing() {
        adListDisplay.stopRefreshing()
    }


}