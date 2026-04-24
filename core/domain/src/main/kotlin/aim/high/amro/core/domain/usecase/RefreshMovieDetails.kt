package aim.high.amro.core.domain.usecase

import aim.high.amro.core.data.repository.MovieRepository
import javax.inject.Inject

class RefreshMovieDetails @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        movieRepository.refreshMovieDetails(movieId)
    }
}
