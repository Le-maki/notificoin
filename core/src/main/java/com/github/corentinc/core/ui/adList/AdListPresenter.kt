package com.github.corentinc.core.ui.adList

import com.github.corentinc.core.ad.Ad

interface AdListPresenter {
    fun presentConnectionError()
    fun presentParsingError()
    fun presentUnknownError()
    fun presentAdList(adList: List<Ad>)
}