package aim.high.amro.feature.detail.state

import aim.high.amro.core.model.MovieFullDetails

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val info: MovieFullDetails) : DetailsUiState
    data class Failure(val cause: Throwable) : DetailsUiState
}
