package com.github.lemaki.repository.search

import androidx.room.Dao
import androidx.room.Query
import com.github.lemaki.repository.dao.BaseDao

@Dao
abstract class SearchDao: BaseDao<SearchEntity>() {

    @Query("SELECT * FROM search WHERE searchUrl = :url")
    abstract fun getSearch(url: String): SearchEntity

    @Query("DELETE FROM search WHERE searchUrl = :url")
    abstract fun removeSearch(url: String)

    @Query("DELETE FROM search")
    abstract fun deleteAll()

    @Query("SELECT * FROM search")
    abstract fun getAll(): List<SearchEntity>
}