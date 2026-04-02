package com.work.activitytracker.feature.add

import androidx.compose.material3.Surface
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
                Surface {
                    AddContent(uiState = AddUiState(), onAction = {})
                }
            }
        }
    }

    @Test
    fun addScreen_empty_landscape() {
        paparazzi.snapshot(deviceConfig = DeviceConfig.PIXEL_6.copy(screenWidth = 2400, screenHeight = 1080, xdpi = 411, ydpi = 411)) {
            SportTrackerTheme {
                Surface {
                    AddContent(uiState = AddUiState(), onAction = {})
                }
            }
        }
    }

    @Test
    fun addScreen_empty_dark() {
        paparazzi.snapshot {
            SportTrackerTheme(darkTheme = true) {
                Surface {
                    AddContent(uiState = AddUiState(), onAction = {})
                }
            }
        }
    }

    @Test
    fun addScreen_remote_selected() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    AddContent(uiState = AddUiState(storageType = StorageType.REMOTE), onAction = {})
                }
            }
        }
    }

    @Test
    fun addScreen_validation_errors() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
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
}
