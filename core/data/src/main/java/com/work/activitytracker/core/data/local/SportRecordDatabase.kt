package com.work.activitytracker.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.activitytracker.core.data.local.dao.SportRecordDao
import com.work.activitytracker.core.data.local.entity.SportRecordEntity

@Database(
    entities = [SportRecordEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class SportRecordDatabase : RoomDatabase() {
    abstract fun sportRecordDao(): SportRecordDao
}
