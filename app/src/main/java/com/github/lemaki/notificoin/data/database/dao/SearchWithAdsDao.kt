package com.github.lemaki.notificoin.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.github.lemaki.notificoin.data.database.entity.SearchWithAdsEntity

@Dao
abstract class SearchWithAdsDao {
	@Transaction
	@Query("SELECT * FROM search")
	abstract fun getSearchWithAds(): List<SearchWithAdsEntity>
}