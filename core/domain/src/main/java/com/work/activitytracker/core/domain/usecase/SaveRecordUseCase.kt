package com.work.activitytracker.core.domain.usecase

import android.util.Log
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import javax.inject.Inject

class SaveRecordUseCase @Inject constructor(
    private val repository: SportRecordRepository,
) {
    suspend operator fun invoke(record: SportRecord) {
        Log.d(TAG, "invoke id=${record.id}")
        repository.saveRecord(record)
    }

    companion object {
        private const val TAG = "SaveRecordUseCase"
    }
}
