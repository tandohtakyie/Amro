package aim.high.amro.core.data.repository

import aim.high.amro.core.common.coroutines.DispatcherProvider
import aim.high.amro.core.data.mapper.asGenres
import aim.high.amro.core.data.mapper.asEntities
import aim.high.amro.core.data.mapper.asGenre
import aim.high.amro.core.database.dao.LocalGenreStore
import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.network.AmroApiService
import aim.high.amro.core.network.model.GenreResponseDto
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

class AmroGenreRepository @Inject constructor(
    private val api: AmroApiService,
    private val genreLocalStore: LocalGenreStore,
    private val dispatchers: DispatcherProvider
) : GenreRepository {

    private val genreDataStore: Store<Unit, List<MovieGenre>> = StoreBuilder
        .from(
            fetcher = Fetcher.of<Unit, List<GenreResponseDto>> {
                withContext(dispatchers.io) {
                    api.getGenres().genres
                }
            },
            sourceOfTruth = SourceOfTruth.of<Unit, List<GenreResponseDto>, List<MovieGenre>>(
                reader = {
                    genreLocalStore.watchGenres().map { entities ->
                        entities.asGenres()
                    }.flowOn(dispatchers.databaseRead)
                },
                writer = { _, dtos ->
                    withContext(dispatchers.databaseWrite) {
                        genreLocalStore.saveGenres(dtos.asEntities())
                    }
                }
            )
        )
        .build()

    override suspend fun getGenres(): List<MovieGenre> =
        withContext(dispatchers.databaseRead) {
            genreLocalStore.fetchGenres().asGenres()
        }

    override suspend fun refreshGenres() {
        val request = StoreReadRequest.fresh(Unit)
        genreDataStore.stream(request)
            .filterNot { it is StoreReadResponse.Loading }
            .first()
    }

    override fun observeGenres(): Flow<StoreReadResponse<List<MovieGenre>>> =
        genreDataStore.stream(StoreReadRequest.cached(Unit, refresh = false))
}
