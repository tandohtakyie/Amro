package aim.high.amro.feature.detail.state

import aim.high.amro.core.model.MovieFullDetails

/**
 * Represents the various visual states of the Movie Details screen.
 */
sealed interface DetailsUiState {
    /** Initial state when details are being fetched or loaded from cache. */
    data object Loading : DetailsUiState

    /** 
     * Success state containing the full movie details.
     * @param info The comprehensive details including budget, revenue, and IMDb links.
     * @param isRefreshing Whether a background refresh is in progress.
     */
    data class Success(
        val info: MovieFullDetails,
        val isRefreshing: Boolean = false
    ) : DetailsUiState

    /** Error state representing a failure to load the movie details. */
    data class Failure(val cause: Throwable) : DetailsUiState
}

/**
 * User interactions that drive state changes in the Details Feature.
 */
sealed interface DetailsEvent {
    /** Manually triggers a network refresh of the details. */
    data object Refresh : DetailsEvent

    /** Retries a failed initial load. */
    data object Retry : DetailsEvent
}
