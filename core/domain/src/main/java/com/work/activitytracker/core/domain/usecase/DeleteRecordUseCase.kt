package com.work.activitytracker.core.domain.usecase

import android.util.Log
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val repository: SportRecordRepository,
) {
    suspend operator fun invoke(id: String, storageType: StorageType) {
        Log.d(TAG, "invoke id=$id storage=$storageType")
        repository.deleteRecord(id, storageType)
    }

    companion object {
        private const val TAG = "DeleteRecordUseCase"
    }
}
