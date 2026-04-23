package aim.high.amro.feature.detail.state

import aim.high.amro.core.model.MovieFullDetails
import androidx.annotation.StringRes

sealed interface DetailsExplorerUiState {
    data object Loading : DetailsExplorerUiState

    data class Success(
        val info: MovieFullDetails,
        val isRefreshing: Boolean = false,
        @get:StringRes val transientMessage: Int? = null
    ) : DetailsExplorerUiState

    data class Failure(
        @get:StringRes val messageRes: Int,
        val connectionError: Boolean
    ) : DetailsExplorerUiState
}

sealed interface DetailsExplorerEvent {
    data object SyncRefresh : DetailsExplorerEvent
    data object ClearAlert : DetailsExplorerEvent
}
