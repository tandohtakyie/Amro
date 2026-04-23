package aim.high.amro.feature.trending.state

import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

sealed interface TrendingExplorerUiState {
    data object Loading : TrendingExplorerUiState

    @Immutable
    data class Loaded(
        val list: List<MovieThumbnail>,
        val filteredList: List<MovieThumbnail>,
        val categories: List<MovieGenre>,
        val activeGenre: Int? = null,
        val sorting: SortingCriteria = SortingCriteria.POPULARITY,
        val direction: SortingDirection = SortingDirection.DESCENDING,
        val refreshing: Boolean = false,
        @get:StringRes val alertMessage: Int? = null
    ) : TrendingExplorerUiState

    data class Failure(
        @get:StringRes val errorRes: Int,
        val isNetworkIssue: Boolean
    ) : TrendingExplorerUiState
}

sealed interface TrendingExplorerEvent {
    data object PullToRefresh : TrendingExplorerEvent
    data class ChangeGenre(val genreId: Int?) : TrendingExplorerEvent
    data class ChangeSorting(val criteria: SortingCriteria) : TrendingExplorerEvent
    data class ToggleSortingDirection(val direction: SortingDirection) : TrendingExplorerEvent
    data object RetryLoad : TrendingExplorerEvent
    data object DismissAlert : TrendingExplorerEvent
}
