package com.github.corentinc.core.repository.search

import com.github.corentinc.core.search.SearchPosition

interface SearchPositionRepository {
    fun getAllSearchPositions(): List<SearchPosition>
    fun getPosition(searchId: Int): SearchPosition
    fun addSearchPosition(searchPosition: SearchPosition): Long
    fun putAll(searchPositionList: List<SearchPosition>)
    fun updateSearchPosition(searchId: Int, position: Int)
    fun deleteAll()
    fun delete(searchId: Int)
}