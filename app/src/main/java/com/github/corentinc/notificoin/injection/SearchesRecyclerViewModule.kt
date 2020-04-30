package com.github.corentinc.notificoin.injection

import com.github.corentinc.notificoin.ui.home.searchesRecyclerView.SearchAdapter
import com.github.corentinc.notificoin.ui.home.searchesRecyclerView.SwipeAndDragHelper
import org.koin.dsl.module

val searchesRecyclerViewModule = module {
    single { SearchAdapter(swipeAndDragHelper = get()) }
    single { SwipeAndDragHelper() }
}