package com.github.corentinc.notificoin.ui.adList

data class AdViewModel(
    val adTitle: String,
    val searchTitle: String,
    val hour: String,
    val date: String,
    val price: String?,
    val url: String
)