package aim.high.amro.core.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieFullDetails(
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val budget: Long,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val imdbId: String?,
    val genres: List<MovieGenre>,
    val adult: Boolean = false,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val homepage: String? = null
) {
    val posterUrl: String
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""

    val backdropUrl: String
        get() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" } ?: ""

    val imdbUrl: String?
        get() = imdbId?.let { "https://www.imdb.com/title/$it" }

    val displayRuntime: String
        get() = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "N/A"

    val displayBudget: String
        get() = if (budget > 0) "$${"%,d".format(budget)}" else "N/A"

    val displayRevenue: String
        get() = if (revenue > 0) "$${"%,d".format(revenue)}" else "N/A"
}
