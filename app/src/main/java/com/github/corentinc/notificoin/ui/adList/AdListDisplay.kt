package com.github.corentinc.notificoin.ui.adList

interface AdListDisplay {
    fun displayConnectionError()
    fun displayParsingError()
    fun displayUnknownError()
    fun displayAdList(adViewDataList: MutableList<AdViewData>)
    fun displayForbiddenError()
    fun displayEmptyList()
    fun hideProgressBar()
    fun stopRefreshing()
    fun displayErrorMessage(isVisible: Boolean)
    fun displayAdsRecyclerView(isVisible: Boolean)
}