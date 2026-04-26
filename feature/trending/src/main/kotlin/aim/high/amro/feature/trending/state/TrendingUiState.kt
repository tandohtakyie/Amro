package aim.high.amro.feature.trending.state

import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import androidx.annotation.StringRes

/**
 * Represents the various visual states of the Trending Feed.
 */
sealed interface TrendingUiState {
    /** Initial state when data is first being requested. */
    data object Loading : TrendingUiState

    /** Error state including a localized message and network classification. */
    data class Failure(
        @StringRes val errorRes: Int,
        val isNetworkIssue: Boolean
    ) : TrendingUiState

    /** 
     * Success state containing the full movie registry and current filter/sort preferences. 
     * 
     * @param list The full source list of trending movies.
     * @param filteredList The list after [activeGenre] and [sorting] preferences are applied.
     * @param categories The available genre categories for filtering.
     * @param activeGenre The currently selected genre ID (null for 'All').
     * @param sorting The current sorting preference (e.g., Popularity).
     * @param direction The current sorting direction (Ascending/Descending).
     * @param alertMessage An optional localized message (e.g., for background sync issues).
     * @param refreshing Whether a manual pull-to-refresh is in progress.
     */
    data class Loaded(
        val list: List<MovieThumbnail>,
        val filteredList: List<MovieThumbnail>,
        val categories: List<MovieGenre>,
        val activeGenre: Int? = null,
        val sorting: SortingCriteria = SortingCriteria.POPULARITY,
        val direction: SortingDirection = SortingDirection.DESCENDING,
        @StringRes val alertMessage: Int? = null,
        val refreshing: Boolean = false
    ) : TrendingUiState
}

/**
 * User interactions that drive state changes in the Trending Feature.
 */
sealed interface TrendingEvent {
    /** Triggered by pull-to-refresh gesture. */
    data object PullToRefresh : TrendingEvent
    
    /** Triggered when a new genre chip is selected. */
    data class ChangeGenre(val genreId: Int?) : TrendingEvent
    
    /** Triggered when a new sorting criteria is chosen in the bottom sheet. */
    data class ChangeSorting(val criteria: SortingCriteria) : TrendingEvent
    
    /** Triggered when the sort direction is toggled. */
    data class ToggleSortingDirection(val direction: SortingDirection) : TrendingEvent
    
    /** Triggered to restart data observation after a failure. */
    data object RetryLoad : TrendingEvent
    
    /** Triggered to clear high-level alert messages. */
    data object DismissAlert : TrendingEvent
}
