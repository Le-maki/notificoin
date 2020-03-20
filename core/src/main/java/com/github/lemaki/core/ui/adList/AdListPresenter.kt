package com.github.lemaki.core.ui.adList

import com.github.lemaki.core.ad.Ad

interface AdListPresenter {
    fun presentConnectionError()
    fun presentParsingError()
    fun presentUnknownError()
    fun presentAdList(adList: List<Ad>)
}