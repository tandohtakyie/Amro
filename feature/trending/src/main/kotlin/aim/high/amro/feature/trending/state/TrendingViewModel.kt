package aim.high.amro.feature.trending.state

import aim.high.amro.core.common.error.asUiErrorMessage
import aim.high.amro.core.common.error.isConnectionIssue
import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.domain.usecase.ApplyMovieFilteringLogic
import aim.high.amro.core.domain.usecase.SyncTrendingMoviesAction
import aim.high.amro.core.domain.usecase.TrendingFeedFlow
import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state of the Trending Feed screen.
 * 
 * This ViewModel bridges the gap between the raw movie stream ([trendingFeed]) and the UI-optimized 
 * filtering logic. It maintains a state machine using [TrendingUiState] to handle loading, 
 * success, and failure scenarios.
 */
@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val trendingFeed: TrendingFeedFlow,
    private val filterLogic: ApplyMovieFilteringLogic,
    private val refreshAction: SyncTrendingMoviesAction
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<TrendingUiState>(TrendingUiState.Loading)
    val viewState: StateFlow<TrendingUiState> = _viewState.asStateFlow()

    private var observationJob: Job? = null

    init {
        beginObservation()
    }

    private fun beginObservation() {
        observationJob?.cancel()
        observationJob = trendingFeed()
            .distinctUntilChanged()
            .onEach { result ->
                _viewState.update { current ->
                    when (result) {
                        is LoadState.Loading -> {
                            current as? TrendingUiState.Loaded ?: TrendingUiState.Loading
                        }

                        is LoadState.Error -> {
                            if (current is TrendingUiState.Loaded && current.list.isNotEmpty()) {
                                current.copy(alertMessage = result.cause.asUiErrorMessage())
                            } else {
                                TrendingUiState.Failure(
                                    errorRes = result.cause.asUiErrorMessage(),
                                    isNetworkIssue = result.cause.isConnectionIssue()
                                )
                            }
                        }

                        is LoadState.Success -> {
                            val data = result.data

                            val (activeG, activeS, activeD) = if (current is TrendingUiState.Loaded) {
                                Triple(current.activeGenre, current.sorting, current.direction)
                            } else {
                                Triple(
                                    null,
                                    SortingCriteria.POPULARITY,
                                    SortingDirection.DESCENDING
                                )
                            }

                            val filtered = filterLogic(
                                movies = data.results,
                                genreIds = if (activeG != null) setOf(activeG) else emptySet(),
                                sortType = activeS,
                                direction = activeD
                            )

                            TrendingUiState.Loaded(
                                list = data.results,
                                filteredList = filtered,
                                categories = data.categories,
                                activeGenre = activeG,
                                sorting = activeS,
                                direction = activeD,
                                alertMessage = data.syncError?.asUiErrorMessage(),
                                refreshing = false
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun handleEvent(event: TrendingEvent) {
        when (event) {
            TrendingEvent.PullToRefresh -> triggerManualSync()
            is TrendingEvent.ChangeGenre -> updateGenreFilter(event.genreId)
            is TrendingEvent.ChangeSorting -> updateSortingType(event.criteria)
            is TrendingEvent.ToggleSortingDirection -> updateSortingDirection(event.direction)
            TrendingEvent.RetryLoad -> beginObservation()
            TrendingEvent.DismissAlert -> clearAlert()
        }
    }

    private fun updateGenreFilter(genreId: Int?) {
        _viewState.update { state ->
            if (state !is TrendingUiState.Loaded) return@update state
            val nextFiltered = filterLogic(
                movies = state.list,
                genreIds = if (genreId != null) setOf(genreId) else emptySet(),
                sortType = state.sorting,
                direction = state.direction
            )
            state.copy(
                activeGenre = genreId,
                filteredList = nextFiltered
            )
        }
    }

    private fun updateSortingType(criteria: SortingCriteria) {
        _viewState.update { state ->
            if (state !is TrendingUiState.Loaded) return@update state
            val nextFiltered = filterLogic(
                movies = state.list,
                genreIds = if (state.activeGenre != null) setOf(state.activeGenre) else emptySet(),
                sortType = criteria,
                direction = state.direction
            )
            state.copy(
                sorting = criteria,
                filteredList = nextFiltered
            )
        }
    }

    private fun updateSortingDirection(direction: SortingDirection) {
        _viewState.update { state ->
            if (state !is TrendingUiState.Loaded) return@update state
            val nextFiltered = filterLogic(
                movies = state.list,
                genreIds = if (state.activeGenre != null) setOf(state.activeGenre) else emptySet(),
                sortType = state.sorting,
                direction = direction
            )
            state.copy(
                direction = direction,
                filteredList = nextFiltered
            )
        }
    }

    private fun triggerManualSync() = viewModelScope.launch {
        _viewState.update { state ->
            if (state is TrendingUiState.Loaded) state.copy(refreshing = true) else state
        }
        refreshAction()
        _viewState.update { state ->
            if (state is TrendingUiState.Loaded) state.copy(refreshing = false) else state
        }
    }

    private fun clearAlert() {
        _viewState.update { state ->
            if (state is TrendingUiState.Loaded) state.copy(alertMessage = null) else state
        }
    }
}
