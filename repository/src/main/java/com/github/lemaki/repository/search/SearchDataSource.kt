package com.github.lemaki.repository.search

import com.github.lemaki.core.search.Search

class SearchDataSource(
    private val searchDao: SearchDao
) {
    fun getAll(): List<Search> {
        return searchDao.getAll().map { it.toSearch() }
    }

    fun put(search: Search) {
        searchDao.insertOrReplace(search.toEntity())
    }

    fun deleteAll() {
        searchDao.deleteAll()
    }

    fun update(id: Int, url: String, title: String) {
        searchDao.updateSearch(id, url, title)
    }
}