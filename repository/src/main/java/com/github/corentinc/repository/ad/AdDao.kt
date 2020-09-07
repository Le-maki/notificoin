package com.github.corentinc.repository.ad

import androidx.room.Dao
import androidx.room.Query
import com.github.corentinc.repository.dao.BaseDao

@Dao
abstract class AdDao: BaseDao<AdEntity>() {

    @Query("SELECT * FROM ads WHERE adId = :id")
    abstract fun getAd(id: Int): AdEntity

    @Query("DELETE FROM ads WHERE adId = :id")
    abstract fun removeAd(id: Int)

    @Query("DELETE FROM ads")
    abstract fun deleteAll()

    @Query("SELECT * FROM ads ORDER BY adPublicationDate")
    abstract fun getAll(): List<AdEntity>

    @Query("DELETE FROM ads WHERE adId in (:idList)")
    abstract fun deleteAll(idList: List<Int>)

    @Query("DELETE FROM ads WHERE searchId = :searchId")
    abstract fun deleteAll(searchId: Int)

    @Query("SELECT * FROM ads WHERE searchId = :searchId")
    abstract fun getAds(searchId: Int): List<AdEntity>
}