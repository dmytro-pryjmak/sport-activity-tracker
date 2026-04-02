package com.work.activitytracker.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sport_records")
data class SportRecordEntity(
    @PrimaryKey val id: String,
    val name: String,
    val location: String,
    val durationMinutes: Int,
    val createdAt: Long,
    val userId: String,
)
