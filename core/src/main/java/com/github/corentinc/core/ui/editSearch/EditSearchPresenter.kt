package com.github.corentinc.core.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError

interface EditSearchPresenter {
    fun presentTitleError(isEmpty: Boolean)
    fun presentUrlError(error: UrlError)
    fun presentValidUrl()
}