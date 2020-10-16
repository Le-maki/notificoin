package com.github.corentinc.notificoin.ui.editSearch

import com.github.corentinc.core.editSearch.UrlError
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter

class EditSearchPresenterImpl : EditSearchPresenter {
    lateinit var editSearchDisplay: EditSearchDisplay

    override fun presentUrlError(error: UrlError) {
        editSearchDisplay.displayUrlError(error)
    }

    override fun presentValidUrl() {
        editSearchDisplay.displayValidUrl()
    }

    override fun presentEditSearch(title: String, url: String) {
        editSearchDisplay.displayEditSearch(title, url)
    }

    override fun presentSaveButton(isEnabled: Boolean) {
        editSearchDisplay.displaySaveButton(isEnabled)
    }

    override fun presentUrlInfo(isVisible: Boolean) {
        editSearchDisplay.displayUrlInfo(isVisible)
    }

    override fun presentCopiedContent(clipBoardText: String) {
        editSearchDisplay.displayCopiedContent(clipBoardText)
    }

    override fun presentUrlButtonDisplayedChild(displayedChildIndex: Int) {
        editSearchDisplay.displayUrlButtonDisplayedChild(displayedChildIndex)
    }

    override fun presentNavigateUp() {
        editSearchDisplay.displayNavigateUp()
    }

    override fun presentNavigateToHomeAfterDeletion() {
        editSearchDisplay.displayNavigateHomeAfterDeletion()
    }
}