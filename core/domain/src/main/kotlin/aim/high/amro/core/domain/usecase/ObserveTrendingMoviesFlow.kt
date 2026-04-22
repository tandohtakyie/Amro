package aim.high.amro.core.domain.usecase

import aim.high.amro.core.data.repository.MovieRepository
import aim.high.amro.core.model.MovieThumbnail
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject

class ObserveTrendingMoviesFlow @Inject constructor(
    private val movieDataBroker: MovieRepository
) {
    operator fun invoke(refresh: Boolean): Flow<StoreReadResponse<List<MovieThumbnail>>> {
        return movieDataBroker.observeTrendingMovies(refresh = refresh)
    }
}
