package com.github.corentinc.core.ui.home

import com.github.corentinc.core.search.Search

interface HomeDisplay {
    fun displayEditSearchScreen(id: Int?, url: String?, title: String?)
    fun displayAdListScreen(searchId: Int)
    fun displayUndoDeleteSearch(search: Search)
    fun displayBatteryWarningFragment(shouldDisplayDefaultDialog: Boolean)
    fun displayEmptySearches()
    fun displaySearches(search: MutableList<Search>)
    fun displayProgressBar()
}
