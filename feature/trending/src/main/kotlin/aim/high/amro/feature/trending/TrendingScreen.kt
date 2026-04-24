package aim.high.amro.feature.trending

import aim.high.amro.feature.trending.components.TrendingContent
import aim.high.amro.feature.trending.state.TrendingViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TrendingScreen(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TrendingViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    TrendingContent(
        uiState = state,
        onEvent = viewModel::handleEvent,
        onMovieClick = onMovieClick,
        modifier = modifier
    )
}
