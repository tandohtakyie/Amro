package aim.high.amro.core.data.repository

import aim.high.amro.core.common.coroutines.DispatcherProvider
import aim.high.amro.core.data.mapper.asDomain
import aim.high.amro.core.data.mapper.asEntities
import aim.high.amro.core.data.mapper.asEntity
import aim.high.amro.core.data.mapper.asThumbnails
import aim.high.amro.core.data.mapper.asThumbnail
import aim.high.amro.core.database.dao.LocalMovieRegistry
import aim.high.amro.core.database.dao.LocalDetailedMovieRepo
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.MovieFullDetails
import aim.high.amro.core.network.AmroApiService
import aim.high.amro.core.network.model.MovieDetailedResponseDto
import aim.high.amro.core.network.model.MovieResponseDto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject

class AmroMovieRepository @Inject constructor(
    private val api: AmroApiService,
    private val movieRegistry: LocalMovieRegistry,
    private val detailsRepo: LocalDetailedMovieRepo,
    private val dispatchers: DispatcherProvider
) : MovieRepository {

    private val detailsCacheStore: Store<Int, MovieFullDetails> = StoreBuilder
        .from(
            fetcher = Fetcher.of<Int, MovieDetailedResponseDto> { id ->
                withContext(dispatchers.io) {
                    api.getMovieDetails(id)
                }
            },
            sourceOfTruth = SourceOfTruth.of<Int, MovieDetailedResponseDto, MovieFullDetails>(
                reader = { id ->
                    detailsRepo.observeDetailedMovie(id).map { entity ->
                        entity?.asDomain()
                    }.flowOn(dispatchers.databaseRead)
                },
                writer = { _, dto ->
                    withContext(dispatchers.databaseWrite) {
                        detailsRepo.recordMovieDetails(dto.asEntity())
                    }
                }
            )
        )
        .build()

    private val trendingGridStore: Store<Unit, List<MovieThumbnail>> = StoreBuilder
        .from(
            fetcher = Fetcher.of<Unit, List<MovieResponseDto>> {
                withContext(dispatchers.io) {
                    (1..5).map { pageIdx ->
                        async { api.getTrendingMovies(page = pageIdx) }
                    }.awaitAll()
                        .flatMap { response -> response.results }
                        .take(100)
                }
            },
            sourceOfTruth = SourceOfTruth.of<Unit, List<MovieResponseDto>, List<MovieThumbnail>>(
                reader = {
                    movieRegistry.observeMovies().map { entities ->
                        entities.asThumbnails()
                    }.flowOn(dispatchers.databaseRead)
                },
                writer = { _, dtos ->
                    withContext(dispatchers.databaseWrite) {
                        movieRegistry.replaceRegistry(dtos.asEntities())
                    }
                }
            )
        )
        .build()

    override suspend fun refreshMovieDetails(movieId: Int) {
        val req = StoreReadRequest.fresh(movieId)
        detailsCacheStore.stream(req)
            .filterNot { it is StoreReadResponse.Loading }
            .first()
    }

    override fun observeTrendingMovies(refresh: Boolean): Flow<StoreReadResponse<List<MovieThumbnail>>> =
        trendingGridStore.stream(StoreReadRequest.cached(Unit, refresh = refresh))

    override suspend fun getTrendingMovies(): List<MovieThumbnail> =
        withContext(dispatchers.databaseRead) {
            movieRegistry.fetchMovies().asThumbnails()
        }

    override suspend fun refreshTrendingMovies() {
        val req = StoreReadRequest.fresh(Unit)
        trendingGridStore.stream(req)
            .filterNot { it is StoreReadResponse.Loading }
            .first()
    }

    override fun observeMovieDetails(movieId: Int): Flow<StoreReadResponse<MovieFullDetails>> =
        detailsCacheStore.stream(StoreReadRequest.cached(movieId, refresh = true))
}
