package com.github.corentinc.notificoin.ui.adList

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.adList.AdListErrorType.*
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.logger.analytics.NotifiCoinEvent
import com.github.corentinc.notificoin.AnalyticsEventSender

class AdListPresenterImpl(
    private val adsListToAdViewModelListTransformer: AdListToAdViewModelListTransformer,
    private val adListViewModel: AdListViewModel
): AdListPresenter {
    private fun presentError(errorType: AdListErrorType) {
        adListViewModel.errorType.value = errorType
    }

    override fun presentConnectionError() {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.LIST_OF_ADS_CONNECTION_ERROR)
        presentError(CONNECTION)
    }

    override fun presentParsingError() {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.LIST_OF_ADS_PARSING_ERROR)
        presentError(PARSING)
    }

    override fun presentUnknownError() {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.LIST_OF_ADS_UNKNOWN_ERROR)
        presentError(UNKNOWN)
    }

    override fun presentAdList(searchAdsPositionList: List<SearchAdsPosition>) {
        adListViewModel.adViewModelList.value =
            adsListToAdViewModelListTransformer.transform(searchAdsPositionList)
    }

    override fun presentForbiddenError() {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.LIST_OF_ADS_FORBIDDEN_ERROR)
        presentError(FORBIDDEN)
    }

    override fun presentEmptyList() {
        presentError(EMPTY)
    }
}