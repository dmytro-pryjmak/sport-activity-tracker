package com.work.activitytracker.core.data.local.mapper

import com.work.activitytracker.core.data.local.entity.SportRecordEntity
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType

internal fun SportRecordEntity.toDomain(): SportRecord = SportRecord(
    id = id,
    name = name,
    location = location,
    durationMinutes = durationMinutes,
    storageType = StorageType.LOCAL,
    createdAt = createdAt,
    userId = userId,
)

internal fun SportRecord.toEntity(): SportRecordEntity = SportRecordEntity(
    id = id,
    name = name,
    location = location,
    durationMinutes = durationMinutes,
    createdAt = createdAt,
    userId = userId,
)
