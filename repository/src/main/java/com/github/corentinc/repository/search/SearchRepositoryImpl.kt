package com.github.corentinc.repository.search

import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.search.SearchPosition

class SearchRepositoryImpl(
    private val searchDataSource: SearchDataSource,
    private val searchPositionRepository: SearchPositionRepository
):
    SearchRepository {
    override fun getAllSearches() = searchDataSource.getAll()
    override fun addSearch(search: Search): Long {
        val id = searchDataSource.put(search)
        searchPositionRepository.addSearchPosition(
            SearchPosition(
                searchPositionRepository.getMaxPosition() + 1,
                searchId = id.toInt()
            )
        )
        return id
    }

    override fun updateSearch(id: Int, url: String, title: String) =
        searchDataSource.update(id, url, title)

    override fun deleteAll() = searchDataSource.deleteAll()
    override fun delete(searchId: Int) {
        searchDataSource.delete(searchId)
    }
}