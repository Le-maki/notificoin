package com.github.corentinc.repository.searchPosition

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.github.corentinc.repository.search.SearchEntity

@Entity(
    tableName = "searchPosition",
    foreignKeys = [
        ForeignKey(
            entity = SearchEntity::class,
            parentColumns = ["id"],
            childColumns = ["searchId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SearchPositionEntity(
    @ColumnInfo(name = "position")
    val position: Int,
    @PrimaryKey
    @ColumnInfo(name = "searchId")
    val searchId: Int
)