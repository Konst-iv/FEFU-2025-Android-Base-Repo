package co.feip.fefu2025

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.navigation.Destinations
import co.feip.fefu2025.presentation.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()

            LaunchedEffect(Unit) {
                handleDeepLink(intent, navController!!)
            }

            AppNavGraph(navController = navController!!)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navController?.let {
            handleDeepLink(intent, it)
        }
    }

    private fun handleDeepLink(intent: Intent, navController: NavHostController) {
        intent.data?.let { uri ->
            when {
                uri.scheme == "mysuperapp" && uri.host == "anime" -> {
                    val animeId = uri.pathSegments.getOrNull(1)?.toIntOrNull() ?: return@let
                    navController.navigate(Destinations.animeDetail(animeId)) {
                        popUpTo(Destinations.MAIN_ROUTE) { inclusive = false }
                    }
                }
                uri.scheme == "https" && uri.host == "feip.co" -> {
                    val animeId = uri.pathSegments.getOrNull(1)?.toIntOrNull() ?: return@let
                    navController.navigate(Destinations.animeDetail(animeId)) {
                        popUpTo(Destinations.MAIN_ROUTE) { inclusive = false }
                    }
                }
            }
        }
    }
}