package aim.high.amro.core.common.result

sealed interface LoadState<out T> {
    data object Loading : LoadState<Nothing>
    data class Success<T>(val data: T) : LoadState<T>
    data class Error(val cause: Throwable) : LoadState<Nothing>
}

inline fun <T, R> LoadState<T>.map(transform: (T) -> R): LoadState<R> {
    return when (this) {
        is LoadState.Loading -> LoadState.Loading
        is LoadState.Success -> LoadState.Success(transform(this.data))
        is LoadState.Error -> LoadState.Error(this.cause)
    }
}
