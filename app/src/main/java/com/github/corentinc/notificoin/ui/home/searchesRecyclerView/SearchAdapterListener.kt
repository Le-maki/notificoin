package com.github.corentinc.notificoin.ui.home.searchesRecyclerView

import com.github.corentinc.core.SearchAdsPosition

interface SearchAdapterListener {
    fun onSearchDeleted(searchAdsPosition: SearchAdsPosition)
}
