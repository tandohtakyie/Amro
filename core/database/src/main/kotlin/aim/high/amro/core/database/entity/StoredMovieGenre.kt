package aim.high.amro.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amro_genres")
data class StoredMovieGenre(
    @ColumnInfo(name = "genre_last_sync")
    val lastUpdated: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "genre_label")
    val name: String,
    
    @PrimaryKey
    @ColumnInfo(name = "uid")
    val id: Int
)
