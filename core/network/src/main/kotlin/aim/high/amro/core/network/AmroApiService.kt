package aim.high.amro.core.network

import aim.high.amro.core.network.model.GenresWrapperDto
import aim.high.amro.core.network.model.MovieDetailedResponseDto
import aim.high.amro.core.network.model.TrendingWrapperDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AmroApiService {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "week",
        @Query("page") page: Int = 1
    ): TrendingWrapperDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailedResponseDto

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresWrapperDto

    companion object {
        const val API_URL = "https://api.themoviedb.org/3/"
    }
}
