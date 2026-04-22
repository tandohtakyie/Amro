package aim.high.amro.core.domain.usecase

import aim.high.amro.core.data.repository.MovieRepository
import javax.inject.Inject

class FetchMovieDetailsAction @Inject constructor(
    private val movieBroker: MovieRepository
) {
    suspend operator fun invoke(id: Int) = movieBroker.refreshMovieDetails(id)
}
