package com.github.corentinc.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.github.corentinc.repository.ad.AdDao
import com.github.corentinc.repository.ad.AdEntity
import com.github.corentinc.repository.ad.AdTypeConverter
import com.github.corentinc.repository.search.SearchDao
import com.github.corentinc.repository.search.SearchEntity
import com.github.corentinc.repository.searchAdsPosition.SearchAdsPositionDao
import com.github.corentinc.repository.searchPosition.SearchPositionDao
import com.github.corentinc.repository.searchPosition.SearchPositionEntity

@Database(
    entities = [AdEntity::class, SearchEntity::class, SearchPositionEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(AdTypeConverter::class)
abstract class NotifiCoinDataBase: RoomDatabase() {

    companion object: DatabaseCompanion {
        override val fileName = "notificoin.db"
        override val migrations: Array<Migration> = emptyArray()
    }

    abstract fun adDao(): AdDao
    abstract fun searchDao(): SearchDao
    abstract fun searchWithAdsDao(): SearchAdsPositionDao
    abstract fun searchPositionDao(): SearchPositionDao
}