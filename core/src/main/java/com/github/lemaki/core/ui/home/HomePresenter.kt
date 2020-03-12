package com.github.lemaki.core.ui.home

import com.github.lemaki.core.ad.Ad

interface HomePresenter {
    fun presentConnectionError()
    fun presentParsingError()
    fun presentUnknownError()
    fun presentAdList(adList: List<Ad>)
    fun presentBatteryWhitelistPermissionAlertDialog()
}
