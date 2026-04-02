package com.work.activitytracker.feature.add

import app.cash.turbine.test
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.session.UserSession
import com.work.activitytracker.core.domain.usecase.SaveRecordUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class AddViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val saveRecordUseCase: SaveRecordUseCase = mockk()
    private val userSession: UserSession = mockk {
        every { currentUserId } returns "user-123"
    }

    private fun createViewModel() = AddViewModel(saveRecordUseCase, userSession)

    @Test
    fun `initial state has default values`() {
        val state = createViewModel().state.value
        assertEquals("", state.name)
        assertEquals(DEFAULT_DURATION_MINUTES, state.durationMinutes)
        assertEquals(StorageType.LOCAL, state.storageType)
        assertNull(state.nameError)
    }

    @Test
    fun `field changes update state and clear errors`() {
        val vm = createViewModel()
        vm.postAction(AddUiAction.NameChange("Morning run"))
        vm.postAction(AddUiAction.LocationChange("Central Park"))
        assertEquals("Morning run", vm.state.value.name)
        assertEquals("Central Park", vm.state.value.location)
        assertNull(vm.state.value.nameError)
    }

    @Test
    fun `duration increases and decreases with clamp`() {
        val vm = createViewModel()
        vm.postAction(AddUiAction.DurationIncrease)
        assertEquals(35, vm.state.value.durationMinutes)
        vm.postAction(AddUiAction.DurationDecrease)
        vm.postAction(AddUiAction.DurationDecrease)
        assertEquals(25, vm.state.value.durationMinutes)
        repeat(100) { vm.postAction(AddUiAction.DurationDecrease) }
        assertEquals(5, vm.state.value.durationMinutes)
        repeat(200) { vm.postAction(AddUiAction.DurationIncrease) }
        assertEquals(600, vm.state.value.durationMinutes)
    }

    @Test
    fun `SaveClick with blank fields sets errors`() = runTest {
        val vm = createViewModel()
        vm.postAction(AddUiAction.SaveClick)
        assertNotNull(vm.state.value.nameError)
        assertNotNull(vm.state.value.locationError)
    }

    @Test
    fun `SaveClick with valid data saves and navigates back`() = runTest {
        coJustRun { saveRecordUseCase(any()) }
        val vm = createViewModel()
        vm.postAction(AddUiAction.NameChange("Morning run"))
        vm.postAction(AddUiAction.LocationChange("Park"))

        vm.state.test {
            awaitItem()
            vm.postAction(AddUiAction.SaveClick)
            assertTrue(awaitItem().isSaving)
            assertTrue(awaitItem().navigateBack)
        }
        coVerify(exactly = 1) { saveRecordUseCase(any<SportRecord>()) }
    }

    @Test
    fun `SaveClick passes correct userId from session`() = runTest {
        var captured: SportRecord? = null
        coEvery { saveRecordUseCase(any()) } coAnswers { captured = firstArg() }
        val vm = createViewModel()
        vm.postAction(AddUiAction.NameChange("Run"))
        vm.postAction(AddUiAction.LocationChange("Park"))
        vm.state.test {
            awaitItem()
            vm.postAction(AddUiAction.SaveClick)
            skipItems(2)
        }
        assertEquals("user-123", captured?.userId)
    }

    @Test
    fun `SaveClick on failure emits ShowSnackbar`() = runTest {
        coEvery { saveRecordUseCase(any()) } throws RuntimeException("Firestore error")
        val vm = createViewModel()
        vm.postAction(AddUiAction.NameChange("Run"))
        vm.postAction(AddUiAction.LocationChange("Park"))

        vm.effects.test {
            vm.postAction(AddUiAction.SaveClick)
            val effect = awaitItem().consume() as AddUiEffect.ShowSnackbar
            assertEquals("Firestore error", effect.message)
        }
    }
}
