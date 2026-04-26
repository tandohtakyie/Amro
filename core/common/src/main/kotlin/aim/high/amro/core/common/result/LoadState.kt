package aim.high.amro.core.common.result

/**
 * A generic sealed interface representing the three primary states of any data operation.
 * 
 * Used across repositories, use cases, and ViewModels to provide a type-safe way 
 * to handle asynchronous data flow.
 */
sealed interface LoadState<out T> {
    /** Indicates the operation is currently in progress. */
    data object Loading : LoadState<Nothing>
    
    /** Indicates the operation completed successfully with the provided [data]. */
    data class Success<T>(val data: T) : LoadState<T>
    
    /** Indicates the operation failed with the provided [cause]. */
    data class Error(val cause: Throwable) : LoadState<Nothing>
}

/**
 * Transforms the data within a [LoadState.Success] using the [transform] function,
 * while preserving [Loading] and [Error] states.
 */
inline fun <T, R> LoadState<T>.map(transform: (T) -> R): LoadState<R> {
    return when (this) {
        is LoadState.Loading -> LoadState.Loading
        is LoadState.Success -> LoadState.Success(transform(this.data))
        is LoadState.Error -> LoadState.Error(this.cause)
    }
}
