package aim.high.amro.core.domain.usecase

import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.data.repository.MovieRepository
import aim.high.amro.core.model.MovieFullDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreReadResponseOrigin
import javax.inject.Inject

class WatchMovieDetailsFlow @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<LoadState<DetailedMovieSnapshot>> {
        return movieRepository.observeMovieDetails(movieId)
            .streamAsDetailedSnapshot()
            .map { accumulator: DetailAccumulator ->
                val metaData = accumulator.metaData
                if (metaData != null) {
                    LoadState.Success(
                        DetailedMovieSnapshot(
                            data = metaData,
                            syncIssue = accumulator.syncIssue
                        )
                    )
                } else if (accumulator.syncIssue != null) {
                    LoadState.Error(accumulator.syncIssue)
                } else {
                    LoadState.Loading
                }
            }
    }
}

private fun Flow<StoreReadResponse<MovieFullDetails>>.streamAsDetailedSnapshot(): Flow<DetailAccumulator> {
    return this.filterNot { it is StoreReadResponse.NoNewData }
        .scan(DetailAccumulator()) { acc, response ->
            val responseContent = response.dataOrNull()

            responseContent != null &&
                    response.origin == StoreReadResponseOrigin.SourceOfTruth

            val metaData = responseContent ?: acc.metaData
            
            val issue = if (response is StoreReadResponse.Error) {
                when (response) {
                    is StoreReadResponse.Error.Exception -> response.error
                    else -> Exception(response.errorMessageOrNull() ?: "Detail sync failed")
                }
            } else {
                null
            }

            val activeIssue = if (response is StoreReadResponse.Data) null else issue

            DetailAccumulator(
                metaData = metaData,
                syncIssue = activeIssue,
                isUpdating = response is StoreReadResponse.Loading
            )
        }
}

private data class DetailAccumulator(
    val metaData: MovieFullDetails? = null,
    val syncIssue: Throwable? = null,
    val isUpdating: Boolean = false
)
