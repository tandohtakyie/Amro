package aim.high.amro.feature.trending.components

import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.feature.trending.state.TrendingEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrendingFeedLoadedView(
    list: List<MovieThumbnail>,
    categories: List<MovieGenre>,
    activeId: Int?,
    isRefreshing: Boolean,
    onEvent: (TrendingEvent) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val genreMap = remember(categories) {
        categories.associate { it.id to it.name }
    }

    Column(modifier = modifier) {
        CategorySelectionBar(
            categories = categories,
            selectedId = activeId,
            onGenreFilter = { onEvent(TrendingEvent.ChangeGenre(it)) }
        )

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onEvent(TrendingEvent.PullToRefresh) }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(list, key = { it.id }) { movie ->
                    val genreNames = remember(movie.genreIds, genreMap) {
                        movie.genreIds.mapNotNull { genreMap[it] }
                    }

                    MovieThumbnailCard(
                        movie = movie,
                        genres = genreNames,
                        onClick = onMovieClick
                    )
                }
            }
        }
    }
}
