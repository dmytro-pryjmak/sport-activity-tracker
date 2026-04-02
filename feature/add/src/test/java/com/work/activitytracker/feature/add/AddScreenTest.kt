package com.work.activitytracker.feature.add

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.theme.SportTrackerTheme
import org.junit.Rule
import org.junit.Test

class AddScreenTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6)

    @Test
    fun addScreen_empty() {
        paparazzi.snapshot {
            SportTrackerTheme {
                AddContent(
                    uiState = AddUiState(),
                    onAction = {},
                )
            }
        }
    }

    @Test
    fun addScreen_empty_dark() {
        paparazzi.snapshot {
            SportTrackerTheme(darkTheme = true) {
                AddContent(
                    uiState = AddUiState(),
                    onAction = {},
                )
            }
        }
    }

    @Test
    fun addScreen_remote_selected() {
        paparazzi.snapshot {
            SportTrackerTheme {
                AddContent(
                    uiState = AddUiState(storageType = StorageType.REMOTE),
                    onAction = {},
                )
            }
        }
    }

    @Test
    fun addScreen_validation_errors() {
        paparazzi.snapshot {
            SportTrackerTheme {
                AddContent(
                    uiState = AddUiState(
                        nameError = "Name is required",
                        locationError = "Location is required",
                    ),
                    onAction = {},
                )
            }
        }
    }
}
