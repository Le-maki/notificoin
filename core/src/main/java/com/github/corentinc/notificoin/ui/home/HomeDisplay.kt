package com.github.corentinc.notificoin.ui.home

import com.github.corentinc.core.search.Search

interface HomeDisplay {
    fun displayEditAdScreen(id: Int, url: String, title: String)
    fun displayAdListScreen(searchId: Int)
    fun displayUndoDeleteSearch(search: Search)
}
