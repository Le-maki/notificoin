package com.github.corentinc.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
abstract class BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(type: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllOrReplace(typeList: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(type: T): Int
}