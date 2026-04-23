package aim.high.amro.core.domain.di

import aim.high.amro.core.data.repository.GenreRepository
import aim.high.amro.core.data.repository.MovieRepository
import aim.high.amro.core.domain.usecase.ApplyMovieFilteringLogic
import aim.high.amro.core.domain.usecase.FetchMovieDetailsAction
import aim.high.amro.core.domain.usecase.SyncTrendingMoviesAction
import aim.high.amro.core.domain.usecase.TrendingFeedFlow
import aim.high.amro.core.domain.usecase.WatchMovieDetailsFlow
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainProviderModule {

    @Provides
    @Singleton
    fun provideTrendingFeedFlow(
        movieRepo: MovieRepository,
        genreRepo: GenreRepository
    ): TrendingFeedFlow = TrendingFeedFlow(genreRepo, movieRepo)

    @Provides
    @Singleton
    fun provideWatchMovieDetailsFlow(
        movieRepo: MovieRepository
    ): WatchMovieDetailsFlow = WatchMovieDetailsFlow(movieRepo)

    @Provides
    @Singleton
    fun provideSyncTrendingMoviesAction(
        movieRepo: MovieRepository
    ): SyncTrendingMoviesAction = SyncTrendingMoviesAction(movieRepo)

    @Provides
    @Singleton
    fun provideFetchMovieDetailsAction(
        movieRepo: MovieRepository
    ): FetchMovieDetailsAction = FetchMovieDetailsAction(movieRepo)

    @Provides
    @Singleton
    fun provideApplyMovieFilteringLogic(): ApplyMovieFilteringLogic = ApplyMovieFilteringLogic()
}
