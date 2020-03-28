package com.github.corentinc.core

import com.github.corentinc.core.repository.search.SearchRepository

class EditSearchInteractor(
    private val searchRepository: SearchRepository
) {
    fun onNavigateUp(id: Int, title: String, url: String) {
        searchRepository.updateSearch(id, url, title)
    }
}