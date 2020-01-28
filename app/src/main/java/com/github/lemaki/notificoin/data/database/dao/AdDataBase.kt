package com.github.lemaki.notificoin.data.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.github.lemaki.notificoin.data.database.DatabaseCompanion
import com.github.lemaki.notificoin.data.database.entity.AdEntity

@Database(
	entities = [AdEntity::class],
	version = 1,
	exportSchema = true
)
abstract class AdDataBase: RoomDatabase() {

	companion object: DatabaseCompanion {
		override val fileName = "course_synchro.db"
		override val migrations: Array<Migration> = emptyArray()
	}

	abstract fun adDao(): AdDao
}