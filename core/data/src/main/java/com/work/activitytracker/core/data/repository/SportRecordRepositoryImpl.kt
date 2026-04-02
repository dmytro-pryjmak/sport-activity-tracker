package com.work.activitytracker.core.data.repository

import android.util.Log
import com.work.activitytracker.core.data.local.dao.SportRecordDao
import com.work.activitytracker.core.data.local.mapper.toDomain
import com.work.activitytracker.core.data.local.mapper.toEntity
import com.work.activitytracker.core.data.remote.mapper.toDomain
import com.work.activitytracker.core.data.remote.mapper.toRemoteMap
import com.work.activitytracker.core.data.remote.source.FirestoreRecordSource
import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import com.work.activitytracker.core.domain.session.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SportRecordRepositoryImpl @Inject constructor(
    private val dao: SportRecordDao,
    private val remoteSource: FirestoreRecordSource,
    private val userSession: UserSession,
) : SportRecordRepository {

    override fun getRecords(filter: RecordsFilter): Flow<List<SportRecord>> {
        Log.d(TAG, "getRecords filter=$filter")

        val localFlow = dao.observeAll().map { entities -> entities.map { it.toDomain() } }

        val userId = userSession.currentUserId
        val remoteFlow: Flow<List<SportRecord>> = if (userId.isNotEmpty()) {
            remoteSource.observeRecords(userId).map { maps -> maps.map { it.toDomain() } }
        } else {
            flowOf(emptyList())
        }

        return when (filter) {
            RecordsFilter.LOCAL -> localFlow
            RecordsFilter.REMOTE -> remoteFlow
            RecordsFilter.ALL -> combine(localFlow, remoteFlow) { local, remote ->
                (local + remote).sortedByDescending { it.createdAt }
            }
        }
    }

    override suspend fun saveRecord(record: SportRecord) {
        when (record.storageType) {
            StorageType.LOCAL -> dao.insert(record.toEntity())
            StorageType.REMOTE -> {
                val userId = userSession.currentUserId.ifEmpty { return }
                remoteSource.save(userId, record.id, record.toRemoteMap())
            }
        }
    }

    override suspend fun deleteRecord(id: String, storageType: StorageType) {
        when (storageType) {
            StorageType.LOCAL -> dao.deleteById(id)
            StorageType.REMOTE -> {
                val userId = userSession.currentUserId.ifEmpty { return }
                remoteSource.delete(userId, id)
            }
        }
    }

    companion object {
        private const val TAG = "Repository"
    }
}
