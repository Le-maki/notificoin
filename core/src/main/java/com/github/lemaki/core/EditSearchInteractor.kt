package com.github.lemaki.core

import com.github.lemaki.core.repository.search.SearchRepository

class EditSearchInteractor(
    private val searchRepository: SearchRepository
) {
    fun onNavigateUp(id: Int, title: String, url: String) {
        searchRepository.updateSearch(id, url, title)
    }
}