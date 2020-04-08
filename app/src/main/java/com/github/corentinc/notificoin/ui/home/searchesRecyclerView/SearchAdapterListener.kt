package com.github.corentinc.notificoin.ui.home.searchesRecyclerView

import com.github.corentinc.core.search.Search

interface SearchAdapterListener {
    fun onSearchDeleted(search: Search)
    fun onSearchClicked(search: Search)
}
