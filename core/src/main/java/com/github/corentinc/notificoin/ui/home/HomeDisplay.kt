package com.github.corentinc.notificoin.ui.home

interface HomeDisplay {
    fun displayEditAdScreen(id: Int, url: String, title: String)
    fun displayAdListScreen(searchId: Int)
}
