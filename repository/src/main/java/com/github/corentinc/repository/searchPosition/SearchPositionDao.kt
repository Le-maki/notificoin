package com.github.corentinc.repository.searchPosition

import androidx.room.Dao
import androidx.room.Query
import com.github.corentinc.repository.dao.BaseDao

@Dao
abstract class SearchPositionDao: BaseDao<SearchPositionEntity>() {

    @Query("SELECT * FROM searchPosition WHERE searchId = :id")
    abstract fun getSearchPosition(id: Int): SearchPositionEntity

    @Query("DELETE FROM searchPosition WHERE searchId = :id")
    abstract fun removeSearchPosition(id: Int)

    @Query("DELETE FROM searchPosition")
    abstract fun deleteAll()

    @Query("SELECT * FROM searchPosition")
    abstract fun getAll(): List<SearchPositionEntity>

    @Query("UPDATE searchPosition SET searchId = :id WHERE position = :position")
    abstract fun updateSearchPosition(id: Int, position: Int)
}