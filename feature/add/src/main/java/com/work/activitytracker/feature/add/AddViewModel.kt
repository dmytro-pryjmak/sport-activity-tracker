package com.work.activitytracker.feature.add

import android.util.Log
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.session.UserSession
import com.work.activitytracker.core.domain.usecase.SaveRecordUseCase
import com.work.activitytracker.core.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val saveRecordUseCase: SaveRecordUseCase,
    private val userSession: UserSession,
) : MviViewModel<AddUiState, AddUiAction, AddUiEffect>(AddUiState()) {

    override suspend fun onAction(action: AddUiAction) {
        when (action) {
            is AddUiAction.NameChange ->
                updateState { state.copy(name = action.value, nameError = null) }
            is AddUiAction.LocationChange ->
                updateState { state.copy(location = action.value, locationError = null) }
            is AddUiAction.StorageTypeSelect ->
                updateState { state.copy(storageType = action.type) }
            AddUiAction.DurationIncrease ->
                updateState { state.copy(durationMinutes = (state.durationMinutes + DURATION_STEP).coerceAtMost(MAX_DURATION)) }
            AddUiAction.DurationDecrease ->
                updateState { state.copy(durationMinutes = (state.durationMinutes - DURATION_STEP).coerceAtLeast(MIN_DURATION)) }
            AddUiAction.SaveClick -> saveRecord()
        }
    }

    private suspend fun saveRecord() {
        if (!validate()) return
        val current = state.value
        updateState { state.copy(isSaving = true) }
        val record = SportRecord(
            id = UUID.randomUUID().toString(),
            name = current.name.trim(),
            location = current.location.trim(),
            durationMinutes = current.durationMinutes,
            storageType = current.storageType,
            createdAt = System.currentTimeMillis(),
            userId = userSession.currentUserId,
        )
        Log.d(TAG, "saveRecord id=${record.id} storage=${record.storageType}")
        runCatching { saveRecordUseCase(record) }
            .onSuccess { updateState { state.copy(navigateBack = true) } }
            .onFailure { e ->
                updateState { state.copy(isSaving = false) }
                emitEffect(AddUiEffect.ShowSnackbar(e.message ?: "Failed to save"))
            }
    }

    private fun validate(): Boolean {
        val current = state.value
        var valid = true
        if (current.name.isBlank()) {
            updateState { state.copy(nameError = "Activity name is required") }
            valid = false
        }
        if (current.location.isBlank()) {
            updateState { state.copy(locationError = "Location is required") }
            valid = false
        }
        return valid
    }

    companion object {
        private const val TAG = "AddViewModel"
        private const val DURATION_STEP = 5
        private const val MIN_DURATION = 5
        private const val MAX_DURATION = 600
    }
}
