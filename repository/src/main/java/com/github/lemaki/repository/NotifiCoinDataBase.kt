package com.github.lemaki.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.github.lemaki.repository.ad.AdDao
import com.github.lemaki.repository.ad.AdEntity
import com.github.lemaki.repository.ad.AdTypeConverter
import com.github.lemaki.repository.search.SearchDao
import com.github.lemaki.repository.search.SearchEntity
import com.github.lemaki.repository.searchWithAds.SearchWithAdsDao

@Database(
    entities = [AdEntity::class, SearchEntity::class],
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
    abstract fun searchWithAdsDao(): SearchWithAdsDao
}