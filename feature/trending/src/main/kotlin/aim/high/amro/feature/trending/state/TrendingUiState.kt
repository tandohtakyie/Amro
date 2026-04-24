package aim.high.amro.feature.trending.state

import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import androidx.annotation.StringRes

sealed interface TrendingUiState {
    data object Loading : TrendingUiState

    data class Failure(
        @StringRes val errorRes: Int,
        val isNetworkIssue: Boolean
    ) : TrendingUiState

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

sealed interface TrendingEvent {
    data object PullToRefresh : TrendingEvent
    data class ChangeGenre(val genreId: Int?) : TrendingEvent
    data class ChangeSorting(val criteria: SortingCriteria) : TrendingEvent
    data class ToggleSortingDirection(val direction: SortingDirection) : TrendingEvent
    data object RetryLoad : TrendingEvent
    data object DismissAlert : TrendingEvent
}
