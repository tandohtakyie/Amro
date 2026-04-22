package aim.high.amro.core.data.di

import aim.high.amro.core.data.repository.AmroGenreRepository
import aim.high.amro.core.data.repository.AmroMovieRepository
import aim.high.amro.core.data.repository.GenreRepository
import aim.high.amro.core.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {

    @Binds
    @Singleton
    abstract fun bindGenreRepository(
        amroGenreRepository: AmroGenreRepository
    ): GenreRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        amroMovieRepository: AmroMovieRepository
    ): MovieRepository
}
