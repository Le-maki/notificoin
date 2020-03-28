package com.github.corentinc.core.repository.search

import com.github.corentinc.core.search.SearchPosition

interface SearchPositionRepository {
    fun getAllSearchPositions(): List<SearchPosition>
    fun addSearchPosition(searchPosition: SearchPosition): Long
    fun updateSearchPosition(searchId: Int, position: Int)
    fun deleteAll()
    fun delete(searchPosition: SearchPosition)
}