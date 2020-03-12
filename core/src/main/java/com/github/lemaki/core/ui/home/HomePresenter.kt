package com.github.lemaki.core.ui.home

import com.github.lemaki.core.ad.Ad
import com.github.lemaki.core.search.Search

interface HomePresenter {
    fun presentConnectionError()
    fun presentParsingError()
    fun presentUnknownError()
    fun presentAdList(adList: List<Ad>)
    fun presentBatteryWhitelistPermissionAlertDialog()
    fun presentSearches(searchList: List<Search>)
}
