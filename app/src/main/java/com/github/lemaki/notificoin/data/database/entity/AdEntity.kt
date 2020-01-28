package com.github.lemaki.notificoin.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	tableName = "ads",
	indices = [Index(value = ["id"], unique = true)]
)
data class AdEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id: Int = 0,
	@ColumnInfo(name = "title")
	val title: String
)