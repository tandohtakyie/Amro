package aim.high.amro.feature.trending.components

import aim.high.amro.core.model.MovieGenre
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategorySelectionBar(
    categories: List<MovieGenre>,
    selectedId: Int?,
    onGenreFilter: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            GenreChip(
                label = "All",
                isActive = selectedId == null,
                onClick = { onGenreFilter(null) }
            )
        }
        items(categories, key = { it.id }) { genre ->
            GenreChip(
                label = genre.name,
                isActive = genre.id == selectedId,
                onClick = { onGenreFilter(genre.id) }
            )
        }
    }
}
