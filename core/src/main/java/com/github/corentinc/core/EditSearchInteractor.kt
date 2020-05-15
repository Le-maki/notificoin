package com.github.corentinc.core

import com.github.corentinc.core.editSearch.UrlError.*
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSearchInteractor(
    private val searchRepository: SearchRepository,
    private val editSearchPresenter: EditSearchPresenter
) {
    companion object {
        private const val REGEX = "^(http://|https://)www\\.leboncoin\\.fr/(recherche/)?.+"
        private const val SEARCH_PATH = "recherche/"
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

    fun onTitleTextChanged(text: CharSequence?) {
        editSearchPresenter.presentTitleError(text?.isBlank() != false)
    }

    fun onUrlTextChanged(text: CharSequence?) {
        val regex = REGEX.toRegex()
        if (text?.isBlank() == false) {
            text.let { regex.find(it) }?.let { matchResult ->
                if (matchResult.groupValues.getOrNull(2) == SEARCH_PATH) {
                    editSearchPresenter.presentValidUrl()
                } else {
                    editSearchPresenter.presentUrlError(NOT_A_SEARCH)
                }
            } ?: run {
                editSearchPresenter.presentUrlError(INVALID_FORMAT)
            }
        } else {
            editSearchPresenter.presentUrlError(EMPTY)
        }
    }
}