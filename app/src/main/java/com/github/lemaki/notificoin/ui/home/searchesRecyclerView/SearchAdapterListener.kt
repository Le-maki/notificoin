package com.github.lemaki.notificoin.ui.home.searchesRecyclerView

import com.github.lemaki.core.search.Search

interface SearchAdapterListener {
    fun onSearchDeleted(search: Search)
}
