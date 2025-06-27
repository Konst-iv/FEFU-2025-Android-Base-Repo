package co.feip.fefu2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.feip.fefu2025.*
import co.feip.fefu2025.data.remote.RetrofitClient
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.domain.usecase.GetAnimeRecommendationsUseCase
import co.feip.fefu2025.domain.usecase.SearchAnimeUseCase
import co.feip.fefu2025.navigation.Destinations
import co.feip.fefu2025.presentation.detail.AnimeDetailViewModel
import co.feip.fefu2025.presentation.main.MainViewModel
import co.feip.fefu2025.presentation.recommendations.RecommendationsViewModel
import co.feip.fefu2025.presentation.search.SearchViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.MAIN_ROUTE
) {
    val repository = remember {
        AnimeRepositoryImpl(apiService = RetrofitClient.apiService)
    }

    val listUseCase = remember { GetAnimeListUseCase(repository) }
    val detailUseCase = remember { GetAnimeDetailUseCase(repository) }
    val searchUseCase = remember { SearchAnimeUseCase(repository) }
    val recommendationsUseCase = remember { GetAnimeRecommendationsUseCase(repository) }

    NavHost(navController, startDestination) {
        composable(Destinations.MAIN_ROUTE) {
            val viewModel: MainViewModel = viewModel(factory = MainViewModel.Factory(listUseCase))
            MainScreen(
                viewModel = viewModel,
                onAnimeClick = { id -> navController.navigate(Destinations.animeDetail(id)) },
                onSearchClick = { navController.navigate(Destinations.SEARCH_ROUTE) }
            )
        }

        composable(
            route = Destinations.ANIME_DETAIL_ROUTE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("id") ?: 0
            AnimeScreen(
                animeId = animeId,
                viewModelFactory = AnimeDetailViewModel.Factory(detailUseCase, animeId),
                onAnimeClick = { id -> navController.navigate(Destinations.animeDetail(id)) },
                onRecommendationsClick = { id ->
                    navController.navigate(Destinations.recommendations(id))
                }
            )
        }

        composable(Destinations.SEARCH_ROUTE) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate(Destinations.animeDetail(id)) },
                viewModelFactory = SearchViewModel.Factory(searchUseCase)
            )
        }

        composable(
            route = Destinations.RECOMMENDATIONS_ROUTE,
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            val viewModel: RecommendationsViewModel = viewModel(
                factory = RecommendationsViewModel.Factory(recommendationsUseCase, animeId)
            )
            RecommendationsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate(Destinations.animeDetail(id)) }
            )
        }
    }
}