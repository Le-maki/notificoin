package com.github.corentinc.repository.searchPosition

import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.search.SearchPosition

class SearchPositionRepositoryImpl(private val searchPositionDataSource: SearchPositionDataSource):
    SearchPositionRepository {
    override fun getAllSearchPositions(): List<SearchPosition> = searchPositionDataSource.getAll()

    override fun getPosition(searchId: Int) = searchPositionDataSource.get(searchId)

    override fun addSearchPosition(searchPosition: SearchPosition): Long =
        searchPositionDataSource.put(searchPosition)

    override fun putAll(searchPositionList: List<SearchPosition>) =
        searchPositionDataSource.putAll(searchPositionList)

    override fun updateSearchPosition(searchId: Int, position: Int) =
        searchPositionDataSource.update(searchId, position)

    override fun deleteAll() = searchPositionDataSource.deleteAll()

    override fun delete(searchId: Int) =
        searchPositionDataSource.delete(searchId)
}