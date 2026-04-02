package com.work.activitytracker.feature.list

import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.mvi.UiAction
import com.work.activitytracker.core.ui.mvi.UiEffect
import com.work.activitytracker.core.ui.mvi.UiState

data class ListUiState(
    val records: List<SportRecord> = emptyList(),
    val filter: RecordsFilter = RecordsFilter.ALL,
    val isLoading: Boolean = false,
    val error: String? = null,
) : UiState

sealed interface ListUiAction : UiAction {
    data class FilterChange(val filter: RecordsFilter) : ListUiAction
    data class DeleteRecord(val id: String, val storageType: StorageType) : ListUiAction
    data object AddClick : ListUiAction
}

sealed interface ListUiEffect : UiEffect {
    data class ShowSnackbar(val message: String) : ListUiEffect
    data object NavigateToAdd : ListUiEffect
}
