package com.github.corentinc.repository.searchPosition

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "searchPosition"
)
data class SearchPositionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "position")
    val position: Int = 0,
    @ColumnInfo(name = "searchId")
    val searchId: Int
)