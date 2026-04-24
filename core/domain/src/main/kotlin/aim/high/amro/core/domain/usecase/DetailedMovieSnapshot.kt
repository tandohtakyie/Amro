package aim.high.amro.core.domain.usecase

import aim.high.amro.core.model.MovieFullDetails

data class DetailedMovieSnapshot(
    val data: MovieFullDetails?,
    val syncIssue: Throwable? = null,
    val isRefreshing: Boolean = false
)
