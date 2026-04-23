package aim.high.amro.core.domain.usecase

import aim.high.amro.core.data.repository.MovieRepository
import javax.inject.Inject

class SyncTrendingMoviesAction @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.refreshTrendingMovies()
}
