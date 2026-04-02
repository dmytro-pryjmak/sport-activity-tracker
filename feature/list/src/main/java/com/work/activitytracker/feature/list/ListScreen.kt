package com.work.activitytracker.feature.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.work.activitytracker.core.domain.model.RecordsFilter
import com.work.activitytracker.core.domain.model.SportRecord
import com.work.activitytracker.core.domain.model.StorageType
import com.work.activitytracker.core.ui.component.SportRecordCard
import com.work.activitytracker.feature.list.R

@Composable
fun ListScreen(
    onNavigateToAdd: () -> Unit,
    viewModel: ListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { event ->
            event.consume()?.let { effect ->
                when (effect) {
                    ListUiEffect.NavigateToAdd -> onNavigateToAdd()
                    is ListUiEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = { ListTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.postAction(ListUiAction.AddClick) },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.cd_add_record),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        ListContent(
            uiState = uiState,
            onAction = viewModel::postAction,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.list_screen_title)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

@Composable
private fun ListContent(
    uiState: ListUiState,
    onAction: (ListUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        if (maxWidth >= WIDE_BREAKPOINT_DP.dp) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.weight(0.45f)) {
                    FilterRow(
                        activeFilter = uiState.filter,
                        onFilterSelected = { onAction(ListUiAction.FilterChange(it)) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                    RecordListContent(uiState = uiState, onAction = onAction)
                }
                Box(
                    modifier = Modifier.weight(0.55f).fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.wide_panel_hint),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp),
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                FilterRow(
                    activeFilter = uiState.filter,
                    onFilterSelected = { onAction(ListUiAction.FilterChange(it)) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
                RecordListContent(uiState = uiState, onAction = onAction)
            }
        }
    }
}

@Composable
private fun FilterRow(
    activeFilter: RecordsFilter,
    onFilterSelected: (RecordsFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        RecordsFilter.entries.forEach { filter ->
            FilterChip(
                selected = activeFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter.label) },
            )
        }
    }
}

@Composable
private fun RecordListContent(
    uiState: ListUiState,
    onAction: (ListUiAction) -> Unit,
) {
    when {
        uiState.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        uiState.error != null -> ErrorState(uiState.error)
        uiState.records.isEmpty() -> EmptyState()
        else -> RecordList(records = uiState.records, onDelete = { id, type ->
            onAction(ListUiAction.DeleteRecord(id, type))
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordList(
    records: List<SportRecord>,
    onDelete: (id: String, storageType: StorageType) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(records, key = { it.id }) { record ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    if (value == SwipeToDismissBoxValue.EndToStart) {
                        onDelete(record.id, record.storageType)
                        true
                    } else false
                },
            )

            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    AnimatedVisibility(
                        visible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(end = 16.dp),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.cd_delete_record),
                                tint = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                },
            ) {
                SportRecordCard(record = record)
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.DirectionsRun,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.empty_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.empty_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

private const val WIDE_BREAKPOINT_DP = 600
