package aim.high.amro.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amro_movie_details")
data class PersistedDetailedMovie(
    @ColumnInfo(name = "detail_last_sync")
    val lastUpdated: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "full_title")
    val title: String,
    
    @ColumnInfo(name = "movie_tagline")
    val tagline: String,
    
    @ColumnInfo(name = "production_status")
    val status: String,
    
    @ColumnInfo(name = "length_minutes")
    val runtime: Int?,
    
    @ColumnInfo(name = "estimated_budget")
    val budget: Long,
    
    @ColumnInfo(name = "actual_revenue")
    val revenue: Long,
    
    @ColumnInfo(name = "rating_avg")
    val voteAverage: Double,
    
    @ColumnInfo(name = "popularity_rank")
    val popularity: Double,
    
    @ColumnInfo(name = "release_day")
    val releaseDate: String,
    
    @ColumnInfo(name = "genres_data_json")
    val genres: String,
    
    @ColumnInfo(name = "plot_description")
    val overview: String,
    
    @ColumnInfo(name = "poster_url_path")
    val posterPath: String?,
    
    @ColumnInfo(name = "backdrop_url_path")
    val backdropPath: String?,
    
    @ColumnInfo(name = "total_vote_count")
    val voteCount: Int,
    
    @ColumnInfo(name = "imdb_reference_id")
    val imdbId: String?,
    
    @ColumnInfo(name = "web_homepage_url")
    val homepage: String?,
    
    @ColumnInfo(name = "content_for_adults")
    val adult: Boolean,
    
    @ColumnInfo(name = "origin_language_iso")
    val originalLanguage: String,
    
    @ColumnInfo(name = "origin_title_raw")
    val originalTitle: String,
    
    @PrimaryKey
    @ColumnInfo(name = "detailed_uid")
    val id: Int
)
