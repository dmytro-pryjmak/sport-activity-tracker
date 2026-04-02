package com.work.activitytracker.feature.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.domain.usecase.DeleteRecordUseCase
import com.work.activitytracker.core.domain.usecase.GetRecordsUseCase
import com.work.activitytracker.core.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getRecordsUseCase: GetRecordsUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase,
) : MviViewModel<ListUiState, ListUiAction, ListUiEffect>(ListUiState(isLoading = true)) {

    private val filter = MutableStateFlow(RecordsFilter.ALL)

    private val localRecords: StateFlow<List<SportRecord>> = getRecordsUseCase(RecordsFilter.LOCAL)
        .catch { e -> Log.e(TAG, "LOCAL error", e) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(SUBSCRIBE_TIMEOUT_MS), emptyList())

    private val remoteRecords: StateFlow<List<SportRecord>> = getRecordsUseCase(RecordsFilter.REMOTE)
        .catch { e -> Log.e(TAG, "REMOTE error", e) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(SUBSCRIBE_TIMEOUT_MS), emptyList())

    init {
        combine(localRecords, remoteRecords, filter) { local, remote, activeFilter ->
            val records = when (activeFilter) {
                RecordsFilter.ALL -> (local + remote).sortedByDescending { it.createdAt }
                RecordsFilter.LOCAL -> local
                RecordsFilter.REMOTE -> remote
            }
            ListUiState(records = records, filter = activeFilter)
        }
        .onEach { newState ->
            Log.d(TAG, "uiState: filter=${newState.filter} records=${newState.records.size}")
            updateState { newState }
        }
        .launchIn(viewModelScope)
    }

    override suspend fun onAction(action: ListUiAction) {
        when (action) {
            is ListUiAction.FilterChange -> filter.value = action.filter
            is ListUiAction.DeleteRecord -> deleteRecord(action.id, action.storageType)
            ListUiAction.AddClick -> emitEffect(ListUiEffect.NavigateToAdd)
        }
    }

    private suspend fun deleteRecord(id: String, storageType: StorageType) {
        runCatching { deleteRecordUseCase(id, storageType) }
            .onFailure { e ->
                Log.e(TAG, "Delete failed", e)
                emitEffect(ListUiEffect.ShowSnackbar(e.message ?: "Delete failed"))
            }
    }

    companion object {
        private const val TAG = "ListViewModel"
        private const val SUBSCRIBE_TIMEOUT_MS = 5_000L
    }
}
