package com.github.corentinc.repository.ad

import androidx.room.TypeConverter
import org.joda.time.DateTime

class AdTypeConverter {
    @TypeConverter
    fun fromTimestampToDateTime(value: Long?) = value?.let { DateTime(it) }

    @TypeConverter
    fun fromDateToTimestamp(date: DateTime?) = date?.millis
}