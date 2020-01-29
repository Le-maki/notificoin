package com.github.lemaki.notificoin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.github.lemaki.notificoin.data.database.dao.AdDao
import com.github.lemaki.notificoin.data.database.dao.SearchDao
import com.github.lemaki.notificoin.data.database.dao.SearchWithAdsDao
import com.github.lemaki.notificoin.data.database.entity.AdEntity
import com.github.lemaki.notificoin.data.database.entity.SearchEntity

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