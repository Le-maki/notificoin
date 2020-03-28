package com.github.corentinc.core.repository.search

import com.github.corentinc.core.search.Search

interface SearchRepository {
    fun getAllSearches(): List<Search>
    fun addSearch(search: Search): Long
    fun updateSearch(id: Int, url: String, title: String)
    fun deleteAll()
    fun delete(search: Search)
}
