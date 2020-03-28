package com.github.corentinc.repository.search

import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.search.Search

class SearchRepositoryImpl(private val searchDataSource: SearchDataSource):
    SearchRepository {
    override fun getAllSearches() = searchDataSource.getAll()
    override fun addSearch(search: Search) = searchDataSource.put(search)
    override fun updateSearch(id: Int, url: String, title: String) =
        searchDataSource.update(id, url, title)
    override fun deleteAll() = searchDataSource.deleteAll()
    override fun delete(search: Search) {
        searchDataSource.delete(search.id)
    }
}