package aim.high.amro.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import aim.high.amro.core.database.entity.PersistedDetailedMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDetailedMovieRepo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordMovieDetails(movie: PersistedDetailedMovie)

    @Query("SELECT * FROM amro_movie_details WHERE detailed_uid = :movieId")
    suspend fun getDetailedMovie(movieId: Int): PersistedDetailedMovie?

    @Query("SELECT * FROM amro_movie_details WHERE detailed_uid = :movieId")
    fun observeDetailedMovie(movieId: Int): Flow<PersistedDetailedMovie?>

    @Query("DELETE FROM amro_movie_details WHERE detailed_uid = :movieId")
    suspend fun deleteMovieDetails(movieId: Int)
}
