package aim.high.amro.core.data.repository

import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.MovieFullDetails
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse

interface MovieRepository {

    suspend fun refreshMovieDetails(movieId: Int)

    fun observeTrendingMovies(refresh: Boolean): Flow<StoreReadResponse<List<MovieThumbnail>>>

    suspend fun refreshTrendingMovies()

    fun observeMovieDetails(movieId: Int): Flow<StoreReadResponse<MovieFullDetails>>
}
