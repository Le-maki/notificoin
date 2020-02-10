package com.github.lemaki.notificoin.data.search

import com.github.lemaki.notificoin.domain.search.Search

class SearchRepository(private val searchDataSource: SearchDataSource) {
	fun getAllSearches() = searchDataSource.getAll()
	fun addSearch(search: Search) = searchDataSource.put(search)
	fun deleteAll() = searchDataSource.deleteAll()
}