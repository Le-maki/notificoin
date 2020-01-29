package com.github.lemaki.notificoin.data.dataSources

import com.github.lemaki.notificoin.data.database.dao.SearchDao
import com.github.lemaki.notificoin.data.transformers.toEntity
import com.github.lemaki.notificoin.data.transformers.toSearch
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