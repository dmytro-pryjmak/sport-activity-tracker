package com.work.activitytracker.core.domain.repository

import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import kotlinx.coroutines.flow.Flow

interface SportRecordRepository {
    fun getRecords(filter: RecordsFilter): Flow<List<SportRecord>>
    suspend fun saveRecord(record: SportRecord)
    suspend fun deleteRecord(id: String, storageType: StorageType)
}
