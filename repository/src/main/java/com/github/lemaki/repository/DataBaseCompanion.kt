package com.github.lemaki.repository

import androidx.room.migration.Migration

interface DatabaseCompanion {
    val fileName: String
    val migrations: Array<Migration>
}