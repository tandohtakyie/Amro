package aim.high.amro.feature.detail.state

import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.domain.usecase.WatchMovieDetailsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val watchMovieDetails: WatchMovieDetailsFlow
) : ViewModel() {

    val uiState: StateFlow<DetailsUiState> = watchMovieDetails(movieId)
        .map { loadState ->
            when (loadState) {
                is LoadState.Loading -> DetailsUiState.Loading
                is LoadState.Error -> DetailsUiState.Failure(loadState.cause)
                is LoadState.Success -> DetailsUiState.Success(loadState.data.data!!)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailsUiState.Loading
        )

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): DetailsViewModel
    }
}
