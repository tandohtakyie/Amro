package aim.high.amro.core.domain.usecase

import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail

data class TrendingFeedSnapshot(
    val results: List<MovieThumbnail>,
    val categories: List<MovieGenre>,
    val syncError: Throwable? = null
)
