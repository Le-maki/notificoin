package com.github.corentinc.notificoin.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter

class EditSearchPresenterImpl(
    private val editSearchViewModel: EditSearchViewModel
): EditSearchPresenter {
    override fun presentUrlError(error: UrlError) {
        editSearchViewModel.urlError.value = error
    }

    override fun presentValidUrl() {
        editSearchViewModel.urlError.value = null
    }

    override fun presentEditSearch(title: String, url: String) {
        editSearchViewModel.title.value = title
        editSearchViewModel.url.value = url
    }

    override fun presentSaveButton(isEnabled: Boolean) {
        editSearchViewModel.isSaveButtonEnabled.value = isEnabled
    }

    override fun presentUrlInfo(isVisible: Boolean) {
        editSearchViewModel.isEditSearchUrlInfoTextVisible.value = isVisible
    }
}