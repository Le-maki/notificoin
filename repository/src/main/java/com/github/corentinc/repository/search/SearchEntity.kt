package com.github.corentinc.repository.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search",
    indices = [Index(value = ["id"], unique = true)]
)
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "searchUrl")
    val searchUrl: String,
    @ColumnInfo(name = "searchTitle")
    val searchTitle: String
)