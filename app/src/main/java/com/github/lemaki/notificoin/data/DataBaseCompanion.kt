package com.github.lemaki.notificoin.data

import androidx.room.migration.Migration

interface DatabaseCompanion {
	val fileName: String
	val migrations: Array<Migration>
}