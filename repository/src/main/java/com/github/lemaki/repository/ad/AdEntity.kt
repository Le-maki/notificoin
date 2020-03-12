package com.github.lemaki.repository.ad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(
    tableName = "ads",
    indices = [Index(value = ["adId"], unique = true)]
)
data class AdEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "adId")
    val adId: Int = 0,
    @ColumnInfo(name = "adTitle")
    val adTitle: String,
    @ColumnInfo(name = "adPublicationDate")
    val publicationDate: DateTime,
    @ColumnInfo(name = "adSearchUrl")
    val searchUrl: String,
    @ColumnInfo(name = "url")
    val url: String
)