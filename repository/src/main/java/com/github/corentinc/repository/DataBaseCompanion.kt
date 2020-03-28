package com.github.corentinc.repository

import androidx.room.migration.Migration

interface DatabaseCompanion {
    val fileName: String
    val migrations: Array<Migration>
}