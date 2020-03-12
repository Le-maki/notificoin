package com.github.lemaki.repository.search

import com.github.lemaki.core.repository.search.SearchRepository
import com.github.lemaki.core.search.Search

class SearchRepositoryImpl(private val searchDataSource: SearchDataSource):
    SearchRepository {
    override fun getAllSearches() = searchDataSource.getAll()
    override fun addSearch(search: Search) = searchDataSource.put(search)
    override fun deleteAll() = searchDataSource.deleteAll()
}