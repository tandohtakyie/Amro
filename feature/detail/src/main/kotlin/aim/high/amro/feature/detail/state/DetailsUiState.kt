package aim.high.amro.feature.detail.state

import aim.high.amro.core.model.MovieFullDetails

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(
        val info: MovieFullDetails,
        val isRefreshing: Boolean = false
    ) : DetailsUiState
    data class Failure(val cause: Throwable) : DetailsUiState
}

sealed interface DetailsEvent {
    data object Refresh : DetailsEvent
    data object Retry : DetailsEvent
}
