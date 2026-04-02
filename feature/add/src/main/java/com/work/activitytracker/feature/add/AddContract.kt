package com.work.activitytracker.feature.add

import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.mvi.UiAction
import com.work.activitytracker.core.ui.mvi.UiEffect
import com.work.activitytracker.core.ui.mvi.UiState

internal const val DEFAULT_DURATION_MINUTES = 30

data class AddUiState(
    val name: String = "",
    val location: String = "",
    val durationMinutes: Int = DEFAULT_DURATION_MINUTES,
    val storageType: StorageType = StorageType.LOCAL,
    val isSaving: Boolean = false,
    val navigateBack: Boolean = false,
    val nameError: String? = null,
    val locationError: String? = null,
) : UiState

sealed interface AddUiAction : UiAction {
    data class NameChange(val value: String) : AddUiAction
    data class LocationChange(val value: String) : AddUiAction
    data class StorageTypeSelect(val type: StorageType) : AddUiAction
    data object DurationIncrease : AddUiAction
    data object DurationDecrease : AddUiAction
    data object SaveClick : AddUiAction
}

sealed interface AddUiEffect : UiEffect {
    data class ShowSnackbar(val message: String) : AddUiEffect
}
