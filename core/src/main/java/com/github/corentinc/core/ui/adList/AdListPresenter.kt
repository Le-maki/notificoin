package com.github.corentinc.core.ui.adList

import com.github.corentinc.core.SearchAdsPosition

interface AdListPresenter {
    fun presentConnectionError()
    fun presentParsingError()
    fun presentUnknownError()
    fun presentAdList(searchAdsPositionList: List<SearchAdsPosition>)
    fun presentForbiddenError()
    fun presentEmptyList()
    fun hideProgressBar()
    fun stopRefreshing()
    fun presentErrorMessage(isVisible: Boolean)
    fun presentAdsRecyclerView(isVisible: Boolean)
}