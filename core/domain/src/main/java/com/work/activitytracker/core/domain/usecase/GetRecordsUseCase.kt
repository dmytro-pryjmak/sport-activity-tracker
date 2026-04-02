package com.work.activitytracker.core.domain.usecase

import android.util.Log
import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(
    private val repository: SportRecordRepository,
) {
    operator fun invoke(filter: RecordsFilter): Flow<List<SportRecord>> {
        Log.d(TAG, "invoke filter=$filter")
        return repository.getRecords(filter)
    }

    companion object {
        private const val TAG = "GetRecordsUseCase"
    }
}
