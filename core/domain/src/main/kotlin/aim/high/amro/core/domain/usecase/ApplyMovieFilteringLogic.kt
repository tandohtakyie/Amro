package aim.high.amro.core.domain.usecase

import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.MovieThumbnail
import javax.inject.Inject

class ApplyMovieFilteringLogic @Inject constructor() {

    operator fun invoke(
        movies: List<MovieThumbnail>,
        genreIds: Set<Int>,
        sortType: SortingCriteria
    ): List<MovieThumbnail> {
        return movies
            .filter { movie ->
                genreIds.isEmpty() || movie.genreIds.any { id -> id in genreIds }
            }
            .sortedWith { m1, m2 ->
                when (sortType) {
                    SortingCriteria.POPULARITY -> m2.popularity.compareTo(m1.popularity)
                    SortingCriteria.TITLE -> m1.title.compareTo(m2.title)
                    SortingCriteria.RELEASE_DATE -> m2.releaseDate.compareTo(m1.releaseDate)
                }
            }
    }
}
