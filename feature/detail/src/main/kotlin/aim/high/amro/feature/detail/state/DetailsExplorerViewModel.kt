package aim.high.amro.feature.detail.state

import aim.high.amro.core.common.error.asUiErrorMessage
import aim.high.amro.core.common.error.isConnectionIssue
import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.domain.usecase.FetchMovieDetailsAction
import aim.high.amro.core.domain.usecase.WatchMovieDetailsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsExplorerViewModel.Factory::class)
class DetailsExplorerViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val dataFlow: WatchMovieDetailsFlow,
    private val syncAction: FetchMovieDetailsAction
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsExplorerUiState>(DetailsExplorerUiState.Loading)
    val uiState: StateFlow<DetailsExplorerUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailsExplorerUiState.Loading
    )

    init {
        establishDataObservation()
    }

    private fun establishDataObservation() {
        dataFlow(movieId)
            .onEach { state ->
                when (state) {
                    is LoadState.Loading -> _uiState.update { DetailsExplorerUiState.Loading }
                    is LoadState.Error -> _uiState.update {
                        DetailsExplorerUiState.Failure(
                            messageRes = state.cause.asUiErrorMessage(),
                            connectionError = state.cause.isConnectionIssue()
                        )
                    }

                    is LoadState.Success -> {
                        val snapshot = state.data
                        val movieInfo = snapshot.data

                        if (movieInfo != null) {
                            _uiState.update {
                                DetailsExplorerUiState.Success(
                                    info = movieInfo,
                                    isRefreshing = false,
                                    transientMessage = snapshot.syncIssue?.asUiErrorMessage()
                                )
                            }
                        } else {
                            _uiState.update { DetailsExplorerUiState.Loading }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onInteraction(event: DetailsExplorerEvent) {
        when (event) {
            DetailsExplorerEvent.SyncRefresh -> performManualSync()
            DetailsExplorerEvent.ClearAlert -> resetAlertMessage()
        }
    }

    private fun performManualSync() = viewModelScope.launch {
        _uiState.update { current ->
            if (current is DetailsExplorerUiState.Success) current.copy(isRefreshing = true) else current
        }
        syncAction(movieId)
        _uiState.update { current ->
            if (current is DetailsExplorerUiState.Success) current.copy(isRefreshing = false) else current
        }
    }

    private fun resetAlertMessage() {
        _uiState.update { current ->
            if (current is DetailsExplorerUiState.Success) current.copy(transientMessage = null) else current
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): DetailsExplorerViewModel
    }
}
