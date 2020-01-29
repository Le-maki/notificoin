package com.github.lemaki.notificoin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.github.lemaki.notificoin.data.ad.AdDao
import com.github.lemaki.notificoin.data.search.SearchDao
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsDao
import com.github.lemaki.notificoin.data.ad.AdEntity
import com.github.lemaki.notificoin.data.search.SearchEntity

@Database(
	entities = [AdEntity::class, SearchEntity::class],
	version = 1,
	exportSchema = true
)
abstract class NotifiCoinDataBase: RoomDatabase() {

	companion object: DatabaseCompanion {
		override val fileName = "notificoin.db"
		override val migrations: Array<Migration> = emptyArray()
	}

	abstract fun adDao(): AdDao
	abstract fun searchDao(): SearchDao
	abstract fun searchWithAdsDao(): SearchWithAdsDao
}