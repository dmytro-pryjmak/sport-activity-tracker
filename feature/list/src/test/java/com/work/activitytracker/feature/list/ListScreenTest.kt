package com.work.activitytracker.feature.list

import androidx.compose.material3.Surface
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.ScreenOrientation
import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.theme.SportTrackerTheme
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6)

    @Test
    fun listScreen_empty() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    ListContent(uiState = ListUiState(), onAction = {})
                }
            }
        }
    }

    @Test
    fun listScreen_loading() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    ListContent(uiState = ListUiState(isLoading = true), onAction = {})
                }
            }
        }
    }

    @Test
    fun listScreen_with_records() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    ListContent(
                        uiState = ListUiState(records = sampleRecords, filter = RecordsFilter.ALL),
                        onAction = {},
                    )
                }
            }
        }
    }

    @Test
    fun listScreen_with_records_landscape() {
        paparazzi.unsafeUpdateConfig(
            deviceConfig = DeviceConfig.PIXEL_6.copy(
                orientation = ScreenOrientation.LANDSCAPE
            )
        )

        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    ListContent(
                        uiState = ListUiState(records = sampleRecords, filter = RecordsFilter.ALL),
                        onAction = {},
                    )
                }
            }
        }
    }

    @Test
    fun listScreen_with_records_dark() {
        paparazzi.snapshot {
            SportTrackerTheme(darkTheme = true) {
                Surface {
                    ListContent(
                        uiState = ListUiState(records = sampleRecords, filter = RecordsFilter.ALL),
                        onAction = {},
                    )
                }
            }
        }
    }

    private val sampleRecords = listOf(
        SportRecord(
            id = "1", name = "Morning Run", location = "Central Park",
            durationMinutes = 45, storageType = StorageType.LOCAL,
            createdAt = 1_700_000_000_000L, userId = "user1",
        ),
        SportRecord(
            id = "2", name = "Evening Swim", location = "Olympic Pool",
            durationMinutes = 60, storageType = StorageType.REMOTE,
            createdAt = 1_700_000_000_000L, userId = "user1",
        ),
        SportRecord(
            id = "3", name = "Cycling", location = "Riverside Trail",
            durationMinutes = 90, storageType = StorageType.LOCAL,
            createdAt = 1_700_000_000_000L, userId = "user1",
        ),
    )
}
