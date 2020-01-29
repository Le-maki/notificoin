package com.github.lemaki.notificoin.data.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	tableName = "search",
	indices = [Index(value = ["searchUrl"], unique = true)]
)
data class SearchEntity (
	@PrimaryKey
	@ColumnInfo(name = "searchUrl")
	val searchUrl: String,
	@ColumnInfo(name = "searchTitle")
	val searchTitle: String
)