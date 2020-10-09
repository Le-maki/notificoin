package com.github.corentinc.notificoin.ui.adList

interface AdListDisplay {
    fun displayConnectionError()
    fun displayParsingError()
    fun displayUnknownError()
    fun displayAdList(adViewModelList: MutableList<AdViewModel>)
    fun displayForbiddenError()
    fun displayEmptyList()
}