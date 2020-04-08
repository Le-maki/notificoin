package com.github.corentinc.core.ui.home

import com.github.corentinc.core.search.Search

interface HomePresenter {
    fun presentBatteryWhitelistPermissionAlertDialog()
    fun presentSearches(search: MutableList<Search>)
    fun presentEditSearchScreen(id: Int, url: String, title: String)
    fun presentAdListFragment(search: Search)
}
