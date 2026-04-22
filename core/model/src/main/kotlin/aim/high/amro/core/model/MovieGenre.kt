package aim.high.amro.core.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieGenre(
    val id: Int,
    val name: String
)
