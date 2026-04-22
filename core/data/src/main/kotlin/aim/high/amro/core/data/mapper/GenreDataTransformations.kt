package aim.high.amro.core.data.mapper

import aim.high.amro.core.database.entity.StoredMovieGenre
import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.network.model.GenreResponseDto

fun StoredMovieGenre.asGenre(): MovieGenre = MovieGenre(
    id = id,
    name = name
)

fun List<GenreResponseDto>.asEntities(): List<StoredMovieGenre> = map { it.toEntity() }

fun GenreResponseDto.toEntity(): StoredMovieGenre = StoredMovieGenre(
    id = id,
    name = name
)

fun List<StoredMovieGenre>.asGenres(): List<MovieGenre> = map { it.asGenre() }
