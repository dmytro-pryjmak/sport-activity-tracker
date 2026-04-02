package com.work.activitytracker.feature.list

import app.cash.turbine.test
import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.usecase.DeleteRecordUseCase
import com.work.activitytracker.core.domain.usecase.GetRecordsUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRecordsUseCase: GetRecordsUseCase = mockk()
    private val deleteRecordUseCase: DeleteRecordUseCase = mockk()

    private fun createViewModel(): ListViewModel {
        every { getRecordsUseCase(any()) } returns flowOf(emptyList())
        return ListViewModel(getRecordsUseCase, deleteRecordUseCase)
    }

    @Test
    fun `initial state has ALL filter and no error`() = runTest {
        val vm = createViewModel()
        vm.state.test {
            val state = awaitItem()
            assertEquals(RecordsFilter.ALL, state.filter)
            assertNull(state.error)
        }
    }

    @Test
    fun `records from local and remote are combined`() = runTest {
        val local = fakeRecord("local")
        val remote = fakeRecord("remote").copy(storageType = StorageType.REMOTE)
        every { getRecordsUseCase(RecordsFilter.LOCAL) } returns flowOf(listOf(local))
        every { getRecordsUseCase(RecordsFilter.REMOTE) } returns flowOf(listOf(remote))

        val vm = ListViewModel(getRecordsUseCase, deleteRecordUseCase)

        vm.state.test {
            assertEquals(2, awaitItem().records.size)
        }
    }

    @Test
    fun `FilterChange updates filter and records`() = runTest {
        every { getRecordsUseCase(RecordsFilter.LOCAL) } returns flowOf(listOf(fakeRecord("local")))
        val vm = createViewModel()
        vm.postAction(ListUiAction.FilterChange(RecordsFilter.LOCAL))

        vm.state.test {
            val state = awaitItem()
            assertEquals(RecordsFilter.LOCAL, state.filter)
            assertEquals(1, state.records.size)
        }
    }

    @Test
    fun `AddClick sends NavigateToAdd effect`() = runTest {
        val vm = createViewModel()
        vm.effects.test {
            vm.postAction(ListUiAction.AddClick)
            assertEquals(ListUiEffect.NavigateToAdd, awaitItem().consume())
        }
    }

    @Test
    fun `DeleteRecord success emits no error`() = runTest {
        coJustRun { deleteRecordUseCase("id1", StorageType.LOCAL) }
        val vm = createViewModel()
        vm.postAction(ListUiAction.DeleteRecord("id1", StorageType.LOCAL))
        vm.state.test {
            assertNull(awaitItem().error)
        }
    }

    @Test
    fun `DeleteRecord failure emits ShowSnackbar`() = runTest {
        coEvery { deleteRecordUseCase(any(), any()) } throws RuntimeException("Network error")
        val vm = createViewModel()
        vm.effects.test {
            vm.postAction(ListUiAction.DeleteRecord("id1", StorageType.REMOTE))
            val effect = awaitItem().consume() as ListUiEffect.ShowSnackbar
            assertEquals("Network error", effect.message)
        }
    }

    private fun fakeRecord(id: String) = SportRecord(
        id = id, name = "Run $id", location = "Park",
        durationMinutes = 30, storageType = StorageType.LOCAL,
        createdAt = 1000L, userId = "user1",
    )
}
