package com.github.corentinc.repository.ad

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.github.corentinc.repository.search.SearchEntity
import org.joda.time.DateTime

@Entity(
    tableName = "ads",
    indices = [Index(value = ["adId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = SearchEntity::class,
            parentColumns = ["id"],
            childColumns = ["searchId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class AdEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "adId")
    val adId: Int = 0,
    @ColumnInfo(name = "adTitle")
    val adTitle: String,
    @ColumnInfo(name = "adPublicationDate")
    val adPublicationDate: DateTime,
    @ColumnInfo(name = "adPrice")
    val adPrice: Int?,
    @ColumnInfo(name = "adUrl")
    val adUrl: String,
    @ColumnInfo(name = "searchId")
    val searchId: Int
)