package aim.high.amro.navigation

import kotlinx.serialization.Serializable

sealed interface AmroAppRoutes {
    @Serializable
    data object TrendingFeed : AmroAppRoutes

    @Serializable
    data class MovieDetail(val id: Int) : AmroAppRoutes
}
