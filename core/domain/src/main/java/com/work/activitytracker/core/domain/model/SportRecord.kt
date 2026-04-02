package com.work.activitytracker.core.domain.model

data class SportRecord(
    val id: String,
    val name: String,
    val location: String,
    val durationMinutes: Int,
    val storageType: StorageType,
    val createdAt: Long,
    val userId: String,
)
