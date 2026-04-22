package aim.high.amro.core.database.di

import android.content.Context
import androidx.room.Room
import aim.high.amro.core.database.LocalPersistenceDb
import aim.high.amro.core.database.dao.LocalGenreStore
import aim.high.amro.core.database.dao.LocalMovieRegistry
import aim.high.amro.core.database.dao.LocalDetailedMovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbPersistenceModule {

    @Provides
    fun provideDetailedMovieRepo(database: LocalPersistenceDb): LocalDetailedMovieRepo = 
        database.detailedMovieRepo()

    @Provides
    fun provideGenreStore(database: LocalPersistenceDb): LocalGenreStore = 
        database.genreStore()

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): LocalPersistenceDb = 
        Room.databaseBuilder(
            context,
            LocalPersistenceDb::class.java,
            LocalPersistenceDb.DB_NAME
        ).build()

    @Provides
    fun provideMovieRegistry(database: LocalPersistenceDb): LocalMovieRegistry = 
        database.movieRegistry()
}
