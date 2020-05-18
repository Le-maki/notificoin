package com.github.corentinc.core.ui.home

import com.github.corentinc.core.search.Search

interface HomePresenter {
    fun presentSearches(search: MutableList<Search>)
    fun presentEditSearchScreen(id: Int? = null, url: String? = null, title: String? = null)
    fun presentAdListFragment(search: Search)
    fun presentUndoDeleteSearch(search: Search)
    fun presentBatteryWarningFragment()
    fun presentEmptySearches()
}
