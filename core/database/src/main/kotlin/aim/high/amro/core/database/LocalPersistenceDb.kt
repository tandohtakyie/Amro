package aim.high.amro.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import aim.high.amro.core.database.dao.LocalGenreStore
import aim.high.amro.core.database.dao.LocalMovieRegistry
import aim.high.amro.core.database.dao.LocalDetailedMovieRepo
import aim.high.amro.core.database.entity.StoredMovieGenre
import aim.high.amro.core.database.entity.PersistedDetailedMovie
import aim.high.amro.core.database.entity.CachedLocalMovie

@Database(
    entities = [
        StoredMovieGenre::class,
        PersistedDetailedMovie::class,
        CachedLocalMovie::class
    ],
    version = 1,
    exportSchema = true
)
abstract class LocalPersistenceDb : RoomDatabase() {
    abstract fun genreStore(): LocalGenreStore
    abstract fun movieRegistry(): LocalMovieRegistry
    abstract fun detailedMovieRepo(): LocalDetailedMovieRepo

    companion object {
        const val DB_NAME = "amro_local_persistence.db"
    }
}
