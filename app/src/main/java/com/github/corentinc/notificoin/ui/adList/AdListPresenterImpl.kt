package com.github.corentinc.notificoin.ui.adList

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.adList.AdListErrorType.*
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.logger.analytics.EventKey.*
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ExceptionThrown
import com.github.corentinc.logger.analytics.NotifiCoinEventException
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.EventException
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.LIST_OF_ADS
import com.github.corentinc.notificoin.AnalyticsEventSender

class AdListPresenterImpl(
    private val adsListToAdViewModelListTransformer: AdListToAdViewModelListTransformer,
    private val adListViewModel: AdListViewModel
): AdListPresenter {
    private fun presentError(errorType: AdListErrorType) {
        adListViewModel.errorType.value = errorType
    }

    override fun presentConnectionError() {
        AnalyticsEventSender.sendEvent(
            ExceptionThrown(
                LIST_OF_ADS_CONNECTION_ERROR,
                EventException(NotifiCoinEventException.CONNECTION),
                Screen(LIST_OF_ADS)
            )
        )
        presentError(CONNECTION)
    }

    override fun presentParsingError() {
        AnalyticsEventSender.sendEvent(
            ExceptionThrown(
                LIST_OF_ADS_PARSING_ERROR,
                EventException(NotifiCoinEventException.PARSING),
                Screen(LIST_OF_ADS)
            )
        )
        presentError(PARSING)
    }

    override fun presentUnknownError() {
        AnalyticsEventSender.sendEvent(
            ExceptionThrown(
                LIST_OF_ADS_UNKNOWN_ERROR,
                EventException(NotifiCoinEventException.UNKNOWN),
                Screen(LIST_OF_ADS)
            )
        )
        presentError(UNKNOWN)
    }

    override fun presentAdList(searchAdsPositionList: List<SearchAdsPosition>) {
        adListViewModel.adViewModelList.value =
            adsListToAdViewModelListTransformer.transform(searchAdsPositionList)
    }

    override fun presentForbiddenError() {
        AnalyticsEventSender.sendEvent(
            ExceptionThrown(
                LIST_OF_ADS_FORBIDDEN_ERROR,
                EventException(NotifiCoinEventException.FORBIDDEN),
                Screen(LIST_OF_ADS)
            )
        )
        presentError(FORBIDDEN)
    }

    override fun presentEmptyList() {
        presentError(EMPTY)
    }
}