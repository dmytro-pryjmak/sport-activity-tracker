package com.work.activitytracker.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.theme.LocalGreen
import com.work.activitytracker.core.ui.theme.LocalGreenContainer
import com.work.activitytracker.core.ui.theme.RemoteBlue
import com.work.activitytracker.core.ui.theme.RemoteBlueContainer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SportRecordCard(
    record: SportRecord,
    modifier: Modifier = Modifier,
) {
    val accentColor = record.accentColor
    val containerColor = record.containerColor
    val typeLabel = record.typeLabel

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(ACCENT_BAR_WIDTH_DP.dp)
                    .height(ACCENT_BAR_HEIGHT_DP.dp)
                    .background(accentColor),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
            ) {
                Text(
                    text = record.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${record.location} · ${record.durationMinutes} min",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = record.createdAt.toFormattedDate(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            SuggestionChip(
                onClick = {},
                label = { Text(text = typeLabel, style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.padding(end = 12.dp),
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = containerColor,
                    labelColor = accentColor,
                ),
                border = SuggestionChipDefaults.suggestionChipBorder(
                    enabled = true,
                    borderColor = accentColor.copy(alpha = 0.4f),
                ),
            )
        }
    }
}

private val SportRecord.accentColor: Color
    get() = when (storageType) {
        StorageType.LOCAL -> LocalGreen
        StorageType.REMOTE -> RemoteBlue
    }

private val SportRecord.containerColor: Color
    get() = when (storageType) {
        StorageType.LOCAL -> LocalGreenContainer
        StorageType.REMOTE -> RemoteBlueContainer
    }

private val SportRecord.typeLabel: String
    get() = when (storageType) {
        StorageType.LOCAL -> "Local"
        StorageType.REMOTE -> "Remote"
    }

private fun Long.toFormattedDate(): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(this))

private const val ACCENT_BAR_WIDTH_DP = 6
private const val ACCENT_BAR_HEIGHT_DP = 80
