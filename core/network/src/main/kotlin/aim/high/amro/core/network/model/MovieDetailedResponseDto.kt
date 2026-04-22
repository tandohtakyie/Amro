package aim.high.amro.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailedResponseDto(
    val id: Int,
    val title: String,
    val tagline: String = "",
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val popularity: Double,
    val budget: Long = 0,
    val revenue: Long = 0,
    val runtime: Int? = null,
    val status: String = "",
    @SerialName("imdb_id")
    val imdbId: String? = null,
    val genres: List<GenreResponseDto> = emptyList(),
    val adult: Boolean = false,
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    val homepage: String? = null
)
