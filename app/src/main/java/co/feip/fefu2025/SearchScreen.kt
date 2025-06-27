package co.feip.fefu2025

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.domain.usecase.SearchAnimeUseCase
import co.feip.fefu2025.presentation.search.SearchScreenState
import co.feip.fefu2025.presentation.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit,
    viewModelFactory: SearchViewModel.Factory
) {
    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }
    val listState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                            viewModel.onQueryChanged(it)
                        },
                        placeholder = { Text("Поиск аниме...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(id = R.drawable.ic_back), "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val currentState = state) {
                is SearchScreenState.Idle -> {
                    InfoMessage("Начните вводить название аниме для поиска.")
                }
                is SearchScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is SearchScreenState.Empty -> {
                    InfoMessage("Ничего не найдено. Попробуйте другой запрос.")
                }
                is SearchScreenState.Error -> {
                    InfoMessage(currentState.message)
                }
                is SearchScreenState.Success -> {
                    AnimeGrid(
                        listState = listState,
                        animeList = currentState.animeList,
                        isLoadingMore = currentState.isLoadingMore,
                        onAnimeClick = onAnimeClick
                    )

                    listState.OnBottomReached {
                        viewModel.loadMoreItems()
                    }
                }
            }
        }
    }
}

@Composable
fun InfoMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun LazyGridState.OnBottomReached(loadMore: () -> Unit) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore.value) {
            loadMore()
        }
    }
}

@Composable
fun AnimeGrid(
    listState: LazyGridState,
    animeList: List<co.feip.fefu2025.domain.model.Anime>,
    isLoadingMore: Boolean,
    onAnimeClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animeList, key = { it.id }) { anime ->
            AnimeCard(
                title = anime.title,
                rating = anime.rating,
                genres = anime.genres,
                imageUrl = anime.imageUrl,
                modifier = Modifier.clickable { onAnimeClick(anime.id) }
            )
        }
        if (isLoadingMore) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}