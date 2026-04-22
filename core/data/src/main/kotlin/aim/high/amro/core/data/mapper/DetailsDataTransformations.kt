package aim.high.amro.core.data.mapper

import aim.high.amro.core.database.entity.PersistedDetailedMovie
import aim.high.amro.core.model.MovieFullDetails
import aim.high.amro.core.network.model.MovieDetailedResponseDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun PersistedDetailedMovie.asDomain(): MovieFullDetails = MovieFullDetails(
    id = id,
    title = title,
    tagline = tagline,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    budget = budget,
    revenue = revenue,
    runtime = runtime,
    status = status,
    imdbId = imdbId,
    genres = Json.decodeFromString(genres),
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    homepage = homepage
)

fun MovieDetailedResponseDto.asEntity(): PersistedDetailedMovie = PersistedDetailedMovie(
    id = id,
    title = title,
    tagline = tagline,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    budget = budget,
    revenue = revenue,
    runtime = runtime,
    status = status,
    imdbId = imdbId,
    genres = Json.encodeToString(genres.map { it.toEntity().asGenre() }),
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    homepage = homepage
)
