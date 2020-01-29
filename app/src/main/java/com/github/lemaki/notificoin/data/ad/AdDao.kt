package com.github.lemaki.notificoin.data.ad

import androidx.room.Dao
import androidx.room.Query
import com.github.lemaki.notificoin.data.dao.BaseDao

@Dao
abstract class AdDao: BaseDao<AdEntity>() {

	@Query("SELECT * FROM ads WHERE adId = :id")
	abstract fun getAd(id: Int): AdEntity

	@Query("DELETE FROM ads WHERE adId = :id")
	abstract fun removeAd(id: Int)

	@Query("DELETE FROM ads")
	abstract fun deleteAll()

	@Query("SELECT * FROM ads")
	abstract fun getAll(): List<AdEntity>
}