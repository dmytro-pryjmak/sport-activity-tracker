package com.work.activitytracker.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object List : Screen

    @Serializable
    data object Add : Screen
}
