package aim.high.amro.core.data.mapper

import aim.high.amro.core.database.entity.CachedLocalMovie
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.network.model.MovieResponseDto

fun CachedLocalMovie.asThumbnail(): MovieThumbnail = MovieThumbnail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    genreIds = genreIds.split(",").mapNotNull { it.toIntOrNull() },
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle
)

fun MovieResponseDto.asEntity(): CachedLocalMovie = CachedLocalMovie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    genreIds = genreIds.joinToString(","),
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle
)

fun MovieResponseDto.asThumbnail(): MovieThumbnail = MovieThumbnail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    genreIds = genreIds,
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle
)

fun List<MovieResponseDto>.asEntities(): List<CachedLocalMovie> = map { it.asEntity() }

fun List<CachedLocalMovie>.asThumbnails(): List<MovieThumbnail> = map { it.asThumbnail() }
