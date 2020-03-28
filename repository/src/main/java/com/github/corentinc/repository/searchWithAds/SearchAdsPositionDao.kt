package com.github.corentinc.repository.searchWithAds

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class SearchAdsPositionDao {
    @Transaction
    @Query("SELECT * FROM search")
    abstract fun getAllSearchAdsPosition(): List<SearchAdsPositionEntity>

    @Transaction
    @Query("SELECT * FROM search WHERE id = :id ")
    abstract fun getSearchAdsPosition(id: Int): SearchAdsPositionEntity
}