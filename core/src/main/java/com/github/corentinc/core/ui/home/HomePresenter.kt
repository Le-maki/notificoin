package com.github.corentinc.core.ui.home

import com.github.corentinc.core.SearchAdsPosition

interface HomePresenter {
    fun presentBatteryWhitelistPermissionAlertDialog()
    fun presentSearches(searchAdsPosition: MutableList<SearchAdsPosition>)
    fun presentEditSearchScreen(id: Int, url: String, title: String)
}
