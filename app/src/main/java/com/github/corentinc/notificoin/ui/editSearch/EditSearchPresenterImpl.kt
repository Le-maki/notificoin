package com.github.corentinc.notificoin.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter

class EditSearchPresenterImpl(
    private val editSearchViewModel: EditSearchViewModel
): EditSearchPresenter {
    override fun presentTitleError(isEmpty: Boolean) {
        if (editSearchViewModel.isTitleEmpty.value != isEmpty) editSearchViewModel.isTitleEmpty.value =
            isEmpty
    }

    override fun presentUrlError(error: UrlError) {
        if (editSearchViewModel.urlError.value != error) editSearchViewModel.urlError.value = error
    }

    override fun presentValidUrl() {
        if (editSearchViewModel.urlError.value != null) editSearchViewModel.urlError.value = null
    }
}