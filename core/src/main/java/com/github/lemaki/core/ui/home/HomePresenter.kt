package com.github.lemaki.core.ui.home

import com.github.lemaki.core.search.Search

interface HomePresenter {
    fun presentBatteryWhitelistPermissionAlertDialog()
    fun presentSearches(searchList: MutableList<Search>)
    fun presentEditSearchScreen(id: Int, url: String, title: String)
}
