package com.github.corentinc.notificoin.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError

interface EditSearchDisplay {
    fun displayUrlError(error: UrlError)
    fun displayValidUrl()
    fun displayEditSearch(title: String, url: String)
    fun displaySaveButton(isEnabled: Boolean)
    fun displayUrlInfo(isVisible: Boolean)
    fun displayCopiedContent(clipBoardText: String)
    fun displayUrlButtonDisplayedChild(displayedChildIndex: Int)
    fun displayNavigateUp()
    fun displayNavigateHomeAfterDeletion()
}