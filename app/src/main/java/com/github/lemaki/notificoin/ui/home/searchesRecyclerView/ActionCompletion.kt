package com.github.lemaki.notificoin.ui.home.searchesRecyclerView

interface ActionCompletion {
    fun onViewMoved(oldPosition: Int, newPosition: Int)
    fun onViewSwiped(position: Int)
}