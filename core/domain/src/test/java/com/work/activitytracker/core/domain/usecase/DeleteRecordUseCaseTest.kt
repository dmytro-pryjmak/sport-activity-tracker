package com.work.activitytracker.core.domain.usecase

import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.repository.SportRecordRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteRecordUseCaseTest {

    private val repository: SportRecordRepository = mockk()
    private val useCase = DeleteRecordUseCase(repository)

    @Test
    fun `invoke delegates delete to repository with local type`() = runTest {
        coJustRun { repository.deleteRecord("id1", StorageType.LOCAL) }

        useCase("id1", StorageType.LOCAL)

        coVerify(exactly = 1) { repository.deleteRecord("id1", StorageType.LOCAL) }
    }

    @Test
    fun `invoke delegates delete to repository with remote type`() = runTest {
        coJustRun { repository.deleteRecord("id2", StorageType.REMOTE) }

        useCase("id2", StorageType.REMOTE)

        coVerify(exactly = 1) { repository.deleteRecord("id2", StorageType.REMOTE) }
    }
}
