package com.work.activitytracker.feature.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.theme.LocalGreen
import com.work.activitytracker.core.ui.theme.LocalGreenContainer
import com.work.activitytracker.core.ui.theme.RemoteBlue
import com.work.activitytracker.core.ui.theme.RemoteBlueContainer
import com.work.activitytracker.feature.add.R

@Composable
fun AddScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.navigateBack) {
        if (uiState.navigateBack) onNavigateBack()
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { event ->
            event.consume()?.let { effect ->
                when (effect) {
                    is AddUiEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = { AddTopBar(onNavigateBack = onNavigateBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        AddContent(
            uiState = uiState,
            onAction = viewModel::postAction,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTopBar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.add_screen_title)) },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_navigate_back),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

@Composable
internal fun AddContent(
    uiState: AddUiState,
    onAction: (AddUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        if (maxWidth >= WIDE_BREAKPOINT_DP.dp) {
            Row(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Column(
                    modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    NameField(uiState = uiState, onAction = onAction)
                    LocationField(uiState = uiState, onAction = onAction)
                }
                Column(
                    modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    DurationStepper(durationMinutes = uiState.durationMinutes, onAction = onAction)
                    StorageSelector(selectedType = uiState.storageType, onAction = onAction)
                    SaveButton(isSaving = uiState.isSaving, onAction = onAction)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                NameField(uiState = uiState, onAction = onAction)
                LocationField(uiState = uiState, onAction = onAction)
                DurationStepper(durationMinutes = uiState.durationMinutes, onAction = onAction)
                StorageSelector(selectedType = uiState.storageType, onAction = onAction)
                SaveButton(isSaving = uiState.isSaving, onAction = onAction)
            }
        }
    }
}

@Composable
private fun NameField(uiState: AddUiState, onAction: (AddUiAction) -> Unit) {
    OutlinedTextField(
        value = uiState.name,
        onValueChange = { onAction(AddUiAction.NameChange(it)) },
        label = { Text(stringResource(R.string.field_name)) },
        placeholder = { Text(stringResource(R.string.field_name_placeholder)) },
        isError = uiState.nameError != null,
        supportingText = uiState.nameError?.let { { Text(it) } },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun LocationField(uiState: AddUiState, onAction: (AddUiAction) -> Unit) {
    OutlinedTextField(
        value = uiState.location,
        onValueChange = { onAction(AddUiAction.LocationChange(it)) },
        label = { Text(stringResource(R.string.field_location)) },
        placeholder = { Text(stringResource(R.string.field_location_placeholder)) },
        isError = uiState.locationError != null,
        supportingText = uiState.locationError?.let { { Text(it) } },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DurationStepper(durationMinutes: Int, onAction: (AddUiAction) -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.field_duration),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            FilledTonalIconButton(onClick = { onAction(AddUiAction.DurationDecrease) }) {
                Icon(Icons.Default.Remove, contentDescription = stringResource(R.string.cd_decrease))
            }
            Text(
                text = stringResource(R.string.duration_format, durationMinutes),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(72.dp),
                textAlign = TextAlign.Center,
            )
            FilledTonalIconButton(onClick = { onAction(AddUiAction.DurationIncrease) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.cd_increase))
            }
        }
    }
}

@Composable
private fun StorageSelector(selectedType: StorageType, onAction: (AddUiAction) -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.field_save_to),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StorageTypeCard(
                label = stringResource(R.string.storage_local),
                icon = Icons.Default.Storage,
                isSelected = selectedType == StorageType.LOCAL,
                selectedColor = LocalGreen,
                selectedContainerColor = LocalGreenContainer,
                onSelected = { onAction(AddUiAction.StorageTypeSelect(StorageType.LOCAL)) },
                modifier = Modifier.weight(1f),
            )
            StorageTypeCard(
                label = stringResource(R.string.storage_remote),
                icon = Icons.Default.Cloud,
                isSelected = selectedType == StorageType.REMOTE,
                selectedColor = RemoteBlue,
                selectedContainerColor = RemoteBlueContainer,
                onSelected = { onAction(AddUiAction.StorageTypeSelect(StorageType.REMOTE)) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StorageTypeCard(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    selectedColor: Color,
    selectedContainerColor: Color,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (isSelected) selectedColor else MaterialTheme.colorScheme.outline
    val containerColor = if (isSelected) selectedContainerColor else MaterialTheme.colorScheme.surface

    OutlinedCard(
        onClick = onSelected,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = if (isSelected) 2.dp else 1.dp, color = borderColor),
        colors = CardDefaults.outlinedCardColors(containerColor = containerColor),
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) selectedColor else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp),
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) selectedColor else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun SaveButton(isSaving: Boolean, onAction: (AddUiAction) -> Unit) {
    Button(
        onClick = { onAction(AddUiAction.SaveClick) },
        enabled = !isSaving,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        if (isSaving) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            Text(stringResource(R.string.btn_save))
        }
    }
}

private const val WIDE_BREAKPOINT_DP = 600
