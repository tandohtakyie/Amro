package aim.high.amro.feature.detail.state

import aim.high.amro.core.common.result.LoadState
import aim.high.amro.core.domain.usecase.DetailedMovieSnapshot
import aim.high.amro.core.domain.usecase.RefreshMovieDetails
import aim.high.amro.core.domain.usecase.WatchMovieDetailsFlow
import aim.high.amro.core.model.MovieFullDetails
import aim.high.amro.core.model.MovieGenre
import app.cash.turbine.test
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
class DetailsViewModelTest {

    private val watchMovieDetails = mockk<WatchMovieDetailsFlow>()
    private val refreshMovieDetails = mockk<RefreshMovieDetails>()

    private val testDispatcher = StandardTestDispatcher()
    private val movieId = 123
    private val detailsFlow = MutableStateFlow<LoadState<DetailedMovieSnapshot>>(LoadState.Loading)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { watchMovieDetails(movieId) } returns detailsFlow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val viewModel = createViewModel()
        assertEquals(DetailsUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `transitions to Success when data is received`() = runTest {
        val details = createDetails(movieId)
        val snapshot = DetailedMovieSnapshot(data = details, syncIssue = null, isRefreshing = false)

        val viewModel = createViewModel()

        viewModel.uiState.test {
            // Initial state
            assertEquals(DetailsUiState.Loading, awaitItem())

            detailsFlow.value = LoadState.Success(snapshot)

            val state = awaitItem()
            assertTrue(state is DetailsUiState.Success)
            assertEquals(details, (state as DetailsUiState.Success).info)
        }
    }

    @Test
    fun `transitions to Failure when error occurs`() = runTest {
        val viewModel = createViewModel()
        val error = Exception("Failed")

        viewModel.uiState.test {
            assertEquals(DetailsUiState.Loading, awaitItem())

            detailsFlow.value = LoadState.Error(error)

            assertTrue(awaitItem() is DetailsUiState.Failure)
        }
    }

    @Test
    fun `triggers refresh when Refresh event received`() = runTest {
        coEvery { refreshMovieDetails(movieId) } returns Unit
        val viewModel = createViewModel()

        viewModel.handleEvent(DetailsEvent.Refresh)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { refreshMovieDetails(movieId) }
    }

    private fun createViewModel() = DetailsViewModel(
        movieId = movieId,
        watchMovieDetails = watchMovieDetails,
        refreshMovieDetails = refreshMovieDetails
    )

    private fun createDetails(id: Int) = MovieFullDetails(
        id = id,
        title = "Movie $id",
        tagline = "Tagline $id",
        overview = "Overview $id",
        posterPath = "/poster$id",
        backdropPath = "/backdrop$id",
        releaseDate = "2024-01-01",
        voteAverage = 8.5,
        voteCount = 200,
        popularity = 50.0,
        budget = 100000000,
        revenue = 200000000,
        runtime = 120,
        status = "Released",
        imdbId = "tt1234567",
        genres = listOf(MovieGenre(1, "Action"))
    )
}
