package com.github.lemaki.core.repository.search

import com.github.lemaki.core.search.Search

interface SearchRepository {
    fun getAllSearches(): List<Search>
    fun addSearch(search: Search)
    fun deleteAll()
}
