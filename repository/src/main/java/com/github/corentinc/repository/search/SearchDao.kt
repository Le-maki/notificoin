package com.github.corentinc.repository.search

import androidx.room.Dao
import androidx.room.Query
import com.github.corentinc.repository.dao.BaseDao

@Dao
abstract class SearchDao: BaseDao<SearchEntity>() {
    @Query("SELECT * FROM search WHERE id = :id")
    abstract fun getSearch(id: Int): SearchEntity

    @Query("DELETE FROM search WHERE id = :id")
    abstract fun removeSearch(id: Int): Int

    @Query("DELETE FROM search")
    abstract fun deleteAll()

    @Query("SELECT * FROM search")
    abstract fun getAll(): List<SearchEntity>

    @Query("UPDATE search SET searchUrl = :url, searchTitle = :title WHERE id = :id")
    abstract fun updateSearch(id: Int, url: String, title: String)
}