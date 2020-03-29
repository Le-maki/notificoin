package com.github.corentinc.repository.searchPosition

import androidx.room.*
import com.github.corentinc.repository.search.SearchEntity

@Entity(
    tableName = "searchPosition",
    indices = [Index(value = ["position"], unique = true)],
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
    @PrimaryKey
    @ColumnInfo(name = "position")
    val position: Int,
    @ColumnInfo(name = "searchId")
    val searchId: Int
)