package com.github.corentinc.core.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError

interface EditSearchPresenter {
    fun presentUrlError(error: UrlError)
    fun presentValidUrl()
    fun presentEditSearch(title: String, url: String)
    fun presentSaveButton(isEnabled: Boolean)
    fun presentUrlInfo(isVisible: Boolean)
    fun presentCopiedContent(clipBoardText: String)
    fun presentUrlButtonDisplayedChild(displayedChildIndex: Int)
    fun presentNavigateUp()
    fun presentNavigateToHomeAfterDeletion()
}