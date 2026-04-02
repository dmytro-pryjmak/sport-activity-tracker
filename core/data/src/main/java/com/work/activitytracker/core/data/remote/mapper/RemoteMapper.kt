package com.work.activitytracker.core.data.remote.mapper

import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType

internal fun Map<String, Any>.toDomain(): SportRecord = SportRecord(
    id = get(FIELD_ID) as? String ?: "",
    name = get(FIELD_NAME) as? String ?: "",
    location = get(FIELD_LOCATION) as? String ?: "",
    durationMinutes = (get(FIELD_DURATION_MINUTES) as? Long)?.toInt() ?: 0,
    storageType = StorageType.REMOTE,
    createdAt = get(FIELD_CREATED_AT) as? Long ?: 0L,
    userId = get(FIELD_USER_ID) as? String ?: "",
)

internal fun SportRecord.toRemoteMap(): Map<String, Any> = mapOf(
    FIELD_NAME to name,
    FIELD_LOCATION to location,
    FIELD_DURATION_MINUTES to durationMinutes,
    FIELD_CREATED_AT to createdAt,
    FIELD_USER_ID to userId,
)

private const val FIELD_ID = "id"
private const val FIELD_NAME = "name"
private const val FIELD_LOCATION = "location"
private const val FIELD_DURATION_MINUTES = "durationMinutes"
private const val FIELD_CREATED_AT = "createdAt"
private const val FIELD_USER_ID = "userId"
