package aim.high.amro.core.domain.usecase

import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class ApplyMovieFilteringLogicTest {

    private val logic = ApplyMovieFilteringLogic()

    private val movies = listOf(
        createMovie(id = 1, title = "B Movie", popularity = 10.0, genres = listOf(1)),
        createMovie(id = 2, title = "A Movie", popularity = 20.0, genres = listOf(2)),
        createMovie(id = 3, title = "C Movie", popularity = 5.0, genres = listOf(1, 2))
    )

    @Test
    fun `filters by genre correctly`() {
        val result = logic(movies, genreIds = setOf(1), sortType = SortingCriteria.POPULARITY)

        assertEquals(2, result.size)
        assertTrue(result.any { it.id == 1 })
        assertTrue(result.any { it.id == 3 })
    }

    @Test
    fun `returns all movies when genreIds is empty`() {
        val result = logic(movies, genreIds = emptySet(), sortType = SortingCriteria.POPULARITY)

        assertEquals(3, result.size)
    }

    @Test
    fun `sorts by popularity descending`() {
        val result = logic(
            movies,
            genreIds = emptySet(),
            sortType = SortingCriteria.POPULARITY,
            direction = SortingDirection.DESCENDING
        )

        assertEquals(2, result[0].id) // 20.0
        assertEquals(1, result[1].id) // 10.0
        assertEquals(3, result[2].id) // 5.0
    }

    @Test
    fun `sorts by title ascending`() {
        val result = logic(
            movies,
            genreIds = emptySet(),
            sortType = SortingCriteria.TITLE,
            direction = SortingDirection.ASCENDING
        )

        assertEquals(2, result[0].id) // A Movie
        assertEquals(1, result[1].id) // B Movie
        assertEquals(3, result[2].id) // C Movie
    }

    private fun createMovie(id: Int, title: String, popularity: Double, genres: List<Int>) =
        MovieThumbnail(
            id = id,
            title = title,
            overview = "Overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = "2024-01-01",
            voteAverage = 8.0,
            voteCount = 100,
            popularity = popularity,
            genreIds = genres
        )

    private fun assertTrue(condition: Boolean) = org.junit.Assert.assertTrue(condition)
}
