package com.github.corentinc.notificoin.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter

class EditSearchPresenterImpl(
    private val editSearchViewModel: EditSearchViewModel
): EditSearchPresenter {
    override fun presentTitleError(isEmpty: Boolean) {
        editSearchViewModel.isTitleEmpty.value =
            isEmpty
    }

    override fun presentUrlError(error: UrlError) {
        editSearchViewModel.urlError.value = error
    }

    override fun presentValidUrl() {
        editSearchViewModel.urlError.value = null
    }
}