package com.github.lemaki.notificoin.data.search

import com.github.lemaki.notificoin.domain.search.Search

class SearchDataSource(
	private val searchDao: SearchDao
) {
	fun getAll(): List<Search> {
		return searchDao.getAll().map { it.toSearch() }
	}

	fun put(search: Search) {
		searchDao.insertOrReplace(search.toEntity())
	}
}