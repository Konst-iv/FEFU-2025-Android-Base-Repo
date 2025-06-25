package co.feip.fefu2025

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.domain.model.Anime
import kotlinx.coroutines.delay
import co.feip.fefu2025.domain.repository.AnimeRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit,
    repository: AnimeRepository
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<Anime>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(query) {
        if (query.length >= 2) {
            isLoading = true
            error = null
            delay(300)
            try {
                results = repository.searchAnime(query)
            } catch (e: Exception) {
                error = e.message
            }
            isLoading = false
        } else {
            results = emptyList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Поиск аниме") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Введите название или жанр") }
            )

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(error!!)
                            Button(onClick = { error = null }) {
                                Text("Повторить")
                            }
                        }
                    }
                }
                results.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(if (query.length >= 2) "Ничего не найдено" else "Введите минимум 2 символа")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(results) { anime ->
                            AnimeCard(
                                title = anime.title,
                                rating = anime.rating,
                                genres = anime.genres,
                                image = painterResource(id = anime.imageResId),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { onAnimeClick(anime.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}