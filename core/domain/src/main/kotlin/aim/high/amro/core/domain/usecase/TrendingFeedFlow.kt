package aim.high.amro.core.domain.usecase

import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.data.repository.GenreRepository
import aim.high.amro.core.data.repository.MovieRepository
import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.scan
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject

class TrendingFeedFlow @Inject constructor(
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<LoadState<TrendingFeedSnapshot>> {
        // Use refresh=true so Store5 always fetches from network on cold start
        // while still returning cached data immediately if available
        val moviesStream = movieRepository.observeTrendingMovies(refresh = true).streamAsSnapshot()
        val genresStream = genreRepository.observeGenres().streamAsSnapshot()

        return combine(
            moviesStream,
            genresStream
        ) { movies: DataAccumulator<List<MovieThumbnail>>, genres: DataAccumulator<List<MovieGenre>> ->
            val movieData = movies.content
            val genreData = genres.content

            when {
                (movies.issue != null && (movieData == null || movieData.isEmpty())) ||
                        (genres.issue != null && (genreData == null || genreData.isEmpty())) -> {
                    LoadState.Error(movies.issue ?: genres.issue!!)
                }

                movieData != null && movieData.isNotEmpty() && genreData != null -> {
                    LoadState.Success(
                        TrendingFeedSnapshot(
                            results = movieData,
                            categories = genreData,
                            syncError = movies.issue ?: genres.issue
                        )
                    )
                }
                // Still waiting for first data
                else -> LoadState.Loading
            }
        }.distinctUntilChanged()
    }
}

/**
 * Converts a Store5 response stream into a [DataAccumulator] stream.
 * The accumulator retains the last known good data so we never go
 * back to a null/loading state once data has been received.
 */
private fun <T> Flow<StoreReadResponse<T>>.streamAsSnapshot(): Flow<DataAccumulator<T>> {
    return this
        .filterNot { it is StoreReadResponse.NoNewData }
        .scan(DataAccumulator<T>()) { dataAccumulator, response ->
            when (response) {
                is StoreReadResponse.Loading -> {
                    dataAccumulator.copy(isSyncing = true, issue = null)
                }

                is StoreReadResponse.Data -> {
                    DataAccumulator(
                        content = response.value,
                        issue = null,
                        isSyncing = false
                    )
                }

                is StoreReadResponse.Error.Exception -> {
                    dataAccumulator.copy(isSyncing = false, issue = response.error)
                }

                is StoreReadResponse.Error.Message -> {
                    dataAccumulator.copy(isSyncing = false, issue = Exception(response.message))
                }

                is StoreReadResponse.NoNewData -> dataAccumulator
                else -> dataAccumulator
            }
        }
        .distinctUntilChanged()
}

private data class DataAccumulator<T>(
    val content: T? = null,
    val issue: Throwable? = null,
    val isSyncing: Boolean = false
)
