package com.github.corentinc.repository.searchPosition

import com.github.corentinc.core.search.SearchPosition

class SearchPositionDataSource(
    private val searchPositionDao: SearchPositionDao
) {
    fun getAll(): List<SearchPosition> {
        return searchPositionDao.getAll().map { it.toSearchPosition() }
    }

    fun get(searchId: Int): SearchPosition {
        return searchPositionDao.getSearchPosition(searchId).toSearchPosition()
    }

    fun put(searchPosition: SearchPosition): Long {
        return searchPositionDao.insertOrReplace(searchPosition.toEntity())
    }

    fun deleteAll() {
        searchPositionDao.deleteAll()
    }

    fun update(searchId: Int, position: Int) {
        searchPositionDao.updateSearchPosition(searchId, position)
    }

    fun delete(searchId: Int) {
        searchPositionDao.removeSearchPosition(searchId)
    }

    fun putAll(searchPositionList: List<SearchPosition>) {
        searchPositionDao.insertAllOrReplace(searchPositionList.map { it.toEntity() })
    }

    fun getMaxPosition(): Int {
        return searchPositionDao.getMaxPosition()
    }
}