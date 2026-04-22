package aim.high.amro.core.domain.usecase

import aim.high.amro.core.data.repository.MovieRepository
import aim.high.amro.core.model.MovieFullDetails
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject

class WatchMovieDetailsFlow @Inject constructor(
    private val dataProvider: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<StoreReadResponse<MovieFullDetails>> {
        return dataProvider.observeMovieDetails(movieId)
    }
}
