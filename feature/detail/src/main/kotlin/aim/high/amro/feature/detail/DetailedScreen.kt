package aim.high.amro.feature.detail

import aim.high.amro.feature.detail.components.DetailedViewContent
import aim.high.amro.feature.detail.state.DetailsViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailedScreen(
    movieId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(
        creationCallback = { factory: DetailsViewModel.Factory ->
            factory.create(movieId)
        }
    )
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DetailedViewContent(
        uiState = state,
        onBack = onBack,
        modifier = modifier
    )
}
