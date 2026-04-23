package aim.high.amro.feature.trending.components

import aim.high.amro.feature.trending.state.TrendingExplorerEvent
import aim.high.amro.feature.trending.state.TrendingExplorerUiState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrendingContent(
    uiState: TrendingExplorerUiState,
    onEvent: (TrendingExplorerEvent) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSortSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Amro",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Black
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { showSortSheet = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Sort & Filter")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is TrendingExplorerUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                is TrendingExplorerUiState.Failure -> {
                    TrendingErrorView(
                        errorRes = uiState.errorRes,
                        onRetry = { onEvent(TrendingExplorerEvent.RetryLoad) }
                    )
                }

                is TrendingExplorerUiState.Loaded -> {
                    TrendingFeedLoadedView(
                        list = uiState.filteredList,
                        categories = uiState.categories,
                        activeId = uiState.activeGenre,
                        isRefreshing = uiState.refreshing,
                        onEvent = onEvent,
                        onMovieClick = onMovieClick
                    )

                    if (showSortSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showSortSheet = false },
                            sheetState = sheetState
                        ) {
                            SortingBottomSheetContent(
                                currentCriteria = uiState.sorting,
                                currentDirection = uiState.direction,
                                onCriteriaSelect = {
                                    onEvent(TrendingExplorerEvent.ChangeSorting(it))
                                },
                                onDirectionSelect = {
                                    onEvent(TrendingExplorerEvent.ToggleSortingDirection(it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
