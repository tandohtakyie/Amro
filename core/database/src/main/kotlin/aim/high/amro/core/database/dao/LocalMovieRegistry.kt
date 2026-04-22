package aim.high.amro.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import aim.high.amro.core.database.entity.CachedLocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalMovieRegistry {

    @Query("DELETE FROM amro_movies")
    suspend fun clearRegistry()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerMovies(movies: List<CachedLocalMovie>)

    @Query("SELECT * FROM amro_movies ORDER BY popularity_score DESC")
    fun observeMovies(): Flow<List<CachedLocalMovie>>

    @Transaction
    suspend fun replaceRegistry(movies: List<CachedLocalMovie>) {
        clearRegistry()
        registerMovies(movies)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerMovie(movie: CachedLocalMovie)

    @Query("SELECT * FROM amro_movies ORDER BY popularity_score DESC")
    suspend fun fetchMovies(): List<CachedLocalMovie>
}
