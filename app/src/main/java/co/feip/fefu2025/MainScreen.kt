package co.feip.fefu2025

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import co.feip.fefu2025.AnimeCard

data class Anime(
    val id: Int,
    val title: String,
    val image: Painter,
    val genres: List<Pair<String, Color>>,
    val rating: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(animeList: List<Anime>) {
    Column {
        TopAppBar(
            title = { Text("Anime Search") },
            actions = {
                IconButton(onClick = { /* TODO: Реализовать функциональность поиска */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(animeList) { anime ->
                AnimeCard(
                    image = anime.image,
                    title = anime.title,
                    genres = anime.genres,
                    rating = anime.rating,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun MainScreenPreview() {
    val animeList = listOf(
        Anime(1, "Attack on Titan", painterResource(R.drawable.cowboy_beebop), listOf("Action" to Color.Red), "9.0"),
        Anime(2, "My Hero Academia", painterResource(R.drawable.cowboy_beebop), listOf("School" to Color.Blue), "8.5"),
        Anime(3, "Demon Slayer", painterResource(R.drawable.cowboy_beebop), listOf("Demons" to Color.Green), "9.5"),
        Anime(4, "One Piece", painterResource(R.drawable.cowboy_beebop), listOf("Adventure" to Color.Yellow), "8.8"),
        Anime(5, "Naruto", painterResource(R.drawable.cowboy_beebop), listOf("Marshal Arts" to Color(0xFFFFA500)), "8.7"), // Orange
        Anime(6, "Death Note", painterResource(R.drawable.cowboy_beebop), listOf("Mystery" to Color(0xFF800080)), "9.2") // Purple
    )

    MainScreen(animeList)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewWrapper() {
    MaterialTheme {
        MainScreenPreview()
    }
}