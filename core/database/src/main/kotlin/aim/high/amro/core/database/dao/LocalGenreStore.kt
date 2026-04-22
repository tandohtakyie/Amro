package aim.high.amro.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import aim.high.amro.core.database.entity.StoredMovieGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalGenreStore {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<StoredMovieGenre>)

    @Query("SELECT * FROM amro_genres ORDER BY genre_label ASC")
    fun watchGenres(): Flow<List<StoredMovieGenre>>

    @Query("DELETE FROM amro_genres")
    suspend fun clearGenres()

    @Query("SELECT * FROM amro_genres ORDER BY genre_label ASC")
    suspend fun fetchGenres(): List<StoredMovieGenre>
}
