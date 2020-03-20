package com.github.lemaki.core.ui.home

import com.github.lemaki.core.search.Search

interface HomePresenter {
    fun presentBatteryWhitelistPermissionAlertDialog()
    fun presentSearches(searchList: List<Search>)
}
