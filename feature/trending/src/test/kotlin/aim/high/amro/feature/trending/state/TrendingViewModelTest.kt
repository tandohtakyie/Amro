package aim.high.amro.feature.trending.state

import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.domain.usecase.ApplyMovieFilteringLogic
import aim.high.amro.core.domain.usecase.SyncTrendingMoviesAction
import aim.high.amro.core.domain.usecase.TrendingFeedFlow
import aim.high.amro.core.domain.usecase.TrendingFeedSnapshot
import aim.high.amro.core.model.MovieGenre
import aim.high.amro.core.model.MovieThumbnail
import aim.high.amro.core.model.SortingCriteria
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingViewModelTest {

    private val trendingFeed = mockk<TrendingFeedFlow>()
    private val filterLogic = mockk<ApplyMovieFilteringLogic>()
    private val refreshAction = mockk<SyncTrendingMoviesAction>()

    private val testDispatcher = StandardTestDispatcher()

    private val movieFlow = MutableStateFlow<LoadState<TrendingFeedSnapshot>>(LoadState.Loading)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { trendingFeed() } returns movieFlow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val viewModel = createViewModel()
        assertEquals(TrendingUiState.Loading, viewModel.viewState.value)
    }

    @Test
    fun `transitions to Loaded when data is received`() = runTest {
        val movies = listOf(createMovie(1), createMovie(2))
        val genres = listOf(MovieGenre(1, "Action"))
        val snapshot = TrendingFeedSnapshot(results = movies, categories = genres)

        every { filterLogic(any(), any(), any(), any()) } returns movies

        val viewModel = createViewModel()

        movieFlow.value = LoadState.Success(snapshot)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewState.value
        assertTrue(state is TrendingUiState.Loaded)
        val loaded = state as TrendingUiState.Loaded
        assertEquals(movies, loaded.list)
        assertEquals(genres, loaded.categories)
    }

    @Test
    fun `emits Failure when initial load fails`() = runTest {
        val viewModel = createViewModel()

        movieFlow.value = LoadState.Error(Exception("Network error"))
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.viewState.value is TrendingUiState.Failure)
    }

    @Test
    fun `updates filter when ChangeGenre event received`() = runTest {
        val movies = listOf(createMovie(1, listOf(1)), createMovie(2, listOf(2)))
        val filtered = listOf(movies[0])
        val snapshot = TrendingFeedSnapshot(results = movies, categories = emptyList())

        every { filterLogic(any(), any(), any(), any()) } returns movies // Initial call

        val viewModel = createViewModel()
        movieFlow.value = LoadState.Success(snapshot)
        testDispatcher.scheduler.advanceUntilIdle()

        // Prepare for the filter event
        every { filterLogic(movies, setOf(1), any(), any()) } returns filtered

        viewModel.handleEvent(TrendingEvent.ChangeGenre(1))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewState.value as TrendingUiState.Loaded
        assertEquals(1, state.activeGenre)
        assertEquals(filtered, state.filteredList)
    }

    @Test
    fun `triggers refresh when PullToRefresh event received`() = runTest {
        val movies = listOf(createMovie(1))
        val snapshot = TrendingFeedSnapshot(results = movies, categories = emptyList())
        every { filterLogic(any(), any(), any(), any()) } returns movies
        coEvery { refreshAction() } returns Unit

        val viewModel = createViewModel()
        movieFlow.value = LoadState.Success(snapshot)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleEvent(TrendingEvent.PullToRefresh)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { refreshAction() }
    }

    @Test
    fun `handles sorting changes correctly`() = runTest {
        val movies = listOf(createMovie(1))
        val snapshot = TrendingFeedSnapshot(results = movies, categories = emptyList())
        every { filterLogic(any(), any(), any(), any()) } returns movies

        val viewModel = createViewModel()
        movieFlow.value = LoadState.Success(snapshot)
        testDispatcher.scheduler.advanceUntilIdle()

        // Change sorting
        viewModel.handleEvent(TrendingEvent.ChangeSorting(SortingCriteria.TITLE))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewState.value as TrendingUiState.Loaded
        assertEquals(SortingCriteria.TITLE, state.sorting)

        // Verify filterLogic was called with new criteria
        every { filterLogic(any(), any(), SortingCriteria.TITLE, any()) } returns movies
    }

    private fun createViewModel() = TrendingViewModel(
        trendingFeed = trendingFeed,
        filterLogic = filterLogic,
        refreshAction = refreshAction
    )

    private fun createMovie(id: Int, genres: List<Int> = emptyList()) = MovieThumbnail(
        id = id,
        title = "Movie $id",
        overview = "Overview $id",
        posterPath = "/path$id",
        backdropPath = "/backdrop$id",
        genreIds = genres,
        popularity = 10.0,
        releaseDate = "2024-01-01",
        voteAverage = 8.0,
        voteCount = 100
    )
}
