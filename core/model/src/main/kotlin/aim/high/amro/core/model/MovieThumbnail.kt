package aim.high.amro.core.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieThumbnail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val genreIds: List<Int>,
    val adult: Boolean = false,
    val originalLanguage: String = "",
    val originalTitle: String = ""
) {
    /**
     * Full URL for the movie poster.
     */
    val posterUrl: String
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""

    /**
     * Full URL for the movie backdrop.
     */
    val backdropUrl: String
        get() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" } ?: ""
}
