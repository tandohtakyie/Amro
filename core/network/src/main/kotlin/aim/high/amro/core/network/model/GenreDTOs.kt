package aim.high.amro.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponseDto(
    val id: Int,
    val name: String
)

@Serializable
data class GenresWrapperDto(
    val genres: List<GenreResponseDto>
)
