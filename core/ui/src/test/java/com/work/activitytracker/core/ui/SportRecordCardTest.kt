package com.work.activitytracker.core.ui

import androidx.compose.material3.Surface
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.component.SportRecordCard
import com.work.activitytracker.core.ui.theme.SportTrackerTheme
import org.junit.Rule
import org.junit.Test

class SportRecordCardTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6)

    @Test
    fun sportRecordCard_local() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    SportRecordCard(record = localRecord)
                }
            }
        }
    }

    @Test
    fun sportRecordCard_remote() {
        paparazzi.snapshot {
            SportTrackerTheme {
                Surface {
                    SportRecordCard(record = remoteRecord)
                }
            }
        }
    }

    @Test
    fun sportRecordCard_local_dark() {
        paparazzi.snapshot {
            SportTrackerTheme(darkTheme = true) {
                Surface {
                    SportRecordCard(record = localRecord)
                }
            }
        }
    }

    @Test
    fun sportRecordCard_remote_dark() {
        paparazzi.snapshot {
            SportTrackerTheme(darkTheme = true) {
                Surface {
                    SportRecordCard(record = remoteRecord)
                }
            }
        }
    }

    private val localRecord = SportRecord(
        id = "1", name = "Morning Run", location = "Central Park",
        durationMinutes = 45, storageType = StorageType.LOCAL,
        createdAt = 1_700_000_000_000L, userId = "user1",
    )

    private val remoteRecord = SportRecord(
        id = "2", name = "Evening Swim", location = "Olympic Pool",
        durationMinutes = 60, storageType = StorageType.REMOTE,
        createdAt = 1_700_000_000_000L, userId = "user1",
    )
}
