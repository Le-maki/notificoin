package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.ui.home.searchesRecyclerView.SearchAdapter
import com.github.lemaki.notificoin.ui.home.searchesRecyclerView.SwipeAndDragHelper
import org.koin.dsl.module

val searchesRecyclerViewModule = module {
    single { SearchAdapter(get()) }
    single { SwipeAndDragHelper() }
}