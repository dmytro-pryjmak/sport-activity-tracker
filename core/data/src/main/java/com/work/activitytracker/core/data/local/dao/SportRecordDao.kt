package com.work.activitytracker.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.work.activitytracker.core.data.local.entity.SportRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportRecordDao {

    @Query("SELECT * FROM sport_records ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<SportRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: SportRecordEntity)

    @Query("DELETE FROM sport_records WHERE id = :id")
    suspend fun deleteById(id: String)
}
