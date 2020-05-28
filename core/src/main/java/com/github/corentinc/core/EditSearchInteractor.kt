package com.github.corentinc.core

import com.github.corentinc.core.editSearch.UrlError.INVALID_FORMAT
import com.github.corentinc.core.editSearch.UrlError.NOT_A_SEARCH
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditSearchInteractor(
    private val searchRepository: SearchRepository,
    private val editSearchPresenter: EditSearchPresenter
) {
    companion object {
        private const val REGEX = "^(http://|https://)www\\.leboncoin\\.fr/(recherche/)?.+"
        private const val SEARCH_PATH = "recherche/"
        private const val DEFAULT_CLIPBOARD_VALUE = "null"
        const val DEFAULT_ID = -1
    }

    fun onNavigateUp(id: Int, title: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.updateSearch(id, url, title)
        }
    }

    fun deleteSearch(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.delete(id)
        }
    }

    fun onTitleTextChanged(titleText: CharSequence?, urlText: String, isUrlValid: Boolean) {
        editSearchPresenter.presentSaveButton(titleText?.isBlank() == false && !urlText.isBlank() && isUrlValid)
    }

    fun onTextChanged(urlText: CharSequence?, titleText: String) {
        val regex = REGEX.toRegex()
        val isUrlNotEmpty = urlText?.isBlank() == false
        var isUrlValid = false
        if (isUrlNotEmpty) {
            urlText.let { regex.find(it!!) }?.let { matchResult ->
                isUrlValid = if (matchResult.groupValues.getOrNull(2) == SEARCH_PATH) {
                    editSearchPresenter.presentValidUrl()
                    true
                } else {
                    editSearchPresenter.presentUrlError(NOT_A_SEARCH)
                    false
                }
            } ?: run {
                editSearchPresenter.presentUrlError(INVALID_FORMAT)
                isUrlValid = false
            }
        } else {
            editSearchPresenter.presentValidUrl()
            isUrlValid = true
        }
        editSearchPresenter.presentSaveButton(isUrlNotEmpty && !titleText.isBlank() && isUrlValid)
    }

    fun onStart(id: Int, title: String, url: String, clipBoardText: String) {
        if (id != DEFAULT_ID) {
            editSearchPresenter.presentEditSearch(title, url)
        }
        val regex = REGEX.toRegex()
        val isValidUrl =
            !clipBoardText.isBlank() && clipBoardText != DEFAULT_CLIPBOARD_VALUE && clipBoardText.let {
                regex.find(
                    it
                )
            }
                ?.let { matchResult ->
                    matchResult.groupValues.getOrNull(2) == SEARCH_PATH
                } ?: run {
                false
            }
        editSearchPresenter.presentUrlButtonDisplayedChild(
            if (isValidUrl) {
                1
            } else {
                0
            }
        )
    }

    fun onSave(id: Int, title: String, url: String) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                if (id == DEFAULT_ID) {
                    searchRepository.addSearch(Search(url = url, title = title))
                } else {
                    searchRepository.updateSearch(id, title, url)
                }
            }
        }
    }

    fun onUrlInfoButtonClicked() {
        editSearchPresenter.presentUrlInfo(true)
    }

    fun onEditSearchUrlPasteButtonClicked(clipBoardText: String) {
        editSearchPresenter.presentCopiedContent(clipBoardText)
    }
}