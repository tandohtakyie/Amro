package aim.high.amro.core.domain.usecase

import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import javax.inject.Inject

class ApplyMovieFilteringLogic @Inject constructor() {

    operator fun invoke(
        movies: List<MovieThumbnail>,
        genreIds: Set<Int>,
        sortType: SortingCriteria,
        direction: SortingDirection = SortingDirection.DESCENDING
    ): List<MovieThumbnail> {
        return movies
            .filter { movie ->
                genreIds.isEmpty() || movie.genreIds.any { id -> id in genreIds }
            }
            .sortedWith { m1, m2 ->
                val comparison = when (sortType) {
                    SortingCriteria.POPULARITY -> m1.popularity.compareTo(m2.popularity)
                    SortingCriteria.TITLE -> m1.title.compareTo(m2.title)
                    SortingCriteria.RELEASE_DATE -> m1.releaseDate.compareTo(m2.releaseDate)
                    SortingCriteria.VOTE_AVERAGE -> m1.voteAverage.compareTo(m2.voteAverage)
                }

                if (direction == SortingDirection.ASCENDING) comparison else -comparison
            }
    }
}
