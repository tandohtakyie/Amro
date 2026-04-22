package aim.high.amro.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amro_movies")
data class CachedLocalMovie(
    @ColumnInfo(name = "record_timestamp")
    val lastUpdated: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "display_title")
    val title: String,
    
    @ColumnInfo(name = "popularity_score")
    val popularity: Double,
    
    @ColumnInfo(name = "average_rating")
    val voteAverage: Double,
    
    @ColumnInfo(name = "release_identifier")
    val releaseDate: String,
    
    @ColumnInfo(name = "plot_summary")
    val overview: String,
    
    @ColumnInfo(name = "genres_ids_csv")
    val genreIds: String,
    
    @ColumnInfo(name = "poster_image_path")
    val posterPath: String?,
    
    @ColumnInfo(name = "backdrop_image_path")
    val backdropPath: String?,
    
    @ColumnInfo(name = "rating_count")
    val voteCount: Int,
    
    @ColumnInfo(name = "is_adult_content")
    val adult: Boolean,
    
    @ColumnInfo(name = "language_code")
    val originalLanguage: String,
    
    @ColumnInfo(name = "original_name")
    val originalTitle: String,
    
    @PrimaryKey
    @ColumnInfo(name = "movie_uid")
    val id: Int
)
