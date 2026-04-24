package aim.high.amro.navigation

import aim.high.amro.feature.detail.DetailedScreen
import aim.high.amro.feature.trending.TrendingScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun AmroNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = AmroAppRoutes.TrendingFeed,
        modifier = modifier
    ) {
        composable<AmroAppRoutes.TrendingFeed> {
            TrendingScreen(
                onMovieClick = { id ->
                    navHostController.navigate(AmroAppRoutes.MovieDetail(id))
                }
            )
        }

        composable<AmroAppRoutes.MovieDetail> { entry ->
            val route: AmroAppRoutes.MovieDetail = entry.toRoute()
            DetailedScreen(
                movieId = route.id,
                onBack = { navHostController.navigateUp() }
            )
        }
    }
}
