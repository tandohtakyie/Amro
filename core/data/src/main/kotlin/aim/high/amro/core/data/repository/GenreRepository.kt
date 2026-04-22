package aim.high.amro.core.data.repository

import aim.high.amro.core.model.MovieGenre
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse

interface GenreRepository {

    suspend fun refreshGenres()

    suspend fun getGenres(): List<MovieGenre>

    fun observeGenres(): Flow<StoreReadResponse<List<MovieGenre>>>
}
