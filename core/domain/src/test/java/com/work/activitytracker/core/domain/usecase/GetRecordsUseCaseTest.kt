package com.work.activitytracker.core.domain.usecase

import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRecordsUseCaseTest {

    private val repository: SportRecordRepository = mockk()
    private val useCase = GetRecordsUseCase(repository)

    @Test
    fun `delegates to repository with given filter`() = runTest {
        val records = listOf(fakeRecord())
        every { repository.getRecords(RecordsFilter.LOCAL) } returns flowOf(records)

        val result = useCase(RecordsFilter.LOCAL).toList().first()

        assertEquals(records, result)
        verify(exactly = 1) { repository.getRecords(RecordsFilter.LOCAL) }
    }

    private fun fakeRecord() = SportRecord(
        id = "1", name = "Morning run", location = "Park",
        durationMinutes = 30, storageType = StorageType.LOCAL,
        createdAt = 1000L, userId = "user1",
    )
}
