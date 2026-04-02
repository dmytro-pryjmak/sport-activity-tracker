package com.work.activitytracker.core.domain.usecase

import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveRecordUseCaseTest {

    private val repository: SportRecordRepository = mockk()
    private val useCase = SaveRecordUseCase(repository)

    @Test
    fun `invoke delegates save to repository`() = runTest {
        val record = fakeRecord()
        coJustRun { repository.saveRecord(record) }

        useCase(record)

        coVerify(exactly = 1) { repository.saveRecord(record) }
    }

    private fun fakeRecord() = SportRecord(
        id = "abc",
        name = "Cycling",
        location = "Forest",
        durationMinutes = 60,
        storageType = StorageType.REMOTE,
        createdAt = 2000L,
        userId = "user1",
    )
}
