package com.github.corentinc.core

import com.github.corentinc.core.repository.search.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSearchInteractor(
    private val searchRepository: SearchRepository
) {
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
}