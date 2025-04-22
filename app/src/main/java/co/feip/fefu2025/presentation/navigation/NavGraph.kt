package co.feip.fefu2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import co.feip.fefu2025.MainScreen
import co.feip.fefu2025.AnimeScreen
import co.feip.fefu2025.RecommendationsScreen
import co.feip.fefu2025.data.repository.MockAnimeRepository
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.navigation.Destinations
import co.feip.fefu2025.presentation.detail.AnimeDetailViewModel
import co.feip.fefu2025.presentation.main.MainViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.MAIN_ROUTE
) {
    val repository = MockAnimeRepository()
    val listUseCase = GetAnimeListUseCase(repository)
    val detailUseCase = GetAnimeDetailUseCase(repository)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.MAIN_ROUTE) {
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModel.Factory(listUseCase)
            )
            MainScreen(
                viewModel = viewModel,
                onAnimeClick = { id ->
                    navController.navigate(Destinations.animeDetail(id))
                }
            )
        }

        composable(
            route = Destinations.ANIME_DETAIL_ROUTE,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "mysuperapp://anime/{id}"
                },
                navDeepLink {
                    uriPattern = "https://feip.co/anime/{id}"
                }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("id") ?: 1
            val viewModel: AnimeDetailViewModel = viewModel(
                factory = AnimeDetailViewModel.Factory(detailUseCase, animeId)
            )

            AnimeScreen(
                animeId = animeId,
                viewModelFactory = AnimeDetailViewModel.Factory(detailUseCase, animeId),
                onAnimeClick = { id ->
                    navController.navigate(Destinations.animeDetail(id))
                },
                onRecommendationsClick = {
                    navController.navigate(Destinations.RECOMMENDATIONS_ROUTE)
                }
            )
        }

        composable(Destinations.RECOMMENDATIONS_ROUTE) {
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModel.Factory(listUseCase)
            )
            RecommendationsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id ->
                    navController.navigate(Destinations.animeDetail(id)) {
                        popUpTo(Destinations.MAIN_ROUTE) { inclusive = false }
                    }
                }
            )
        }
    }
}