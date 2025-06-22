package co.feip.fefu2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.detail.AnimeDetailViewModel
import co.feip.fefu2025.RatingBarChart
import co.feip.fefu2025.AnimeCard
import co.feip.fefu2025.data.repository.MockAnimeRepository
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import co.feip.fefu2025.presentation.detail.AnimeDetailState
import co.feip.fefu2025.ui.theme.getGenreColor


@Composable
fun AnimeScreenContent(
    anime: Anime,
    onAnimeClick: (Int) -> Unit,
    onRecommendationsClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
                .padding(horizontal = 16.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(24.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            Image(
                painter = painterResource(id = anime.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val fontSize: TextUnit = when {
                anime.title.length <= 10 -> 30.sp
                anime.title.length <= 15 -> 26.sp
                anime.title.length <= 25 -> 22.sp
                else -> 20.sp
            }

            Text(
                text = anime.title,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp)
            )

            anime.info?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
            }

            AnimeScreenGenreTags(genres = anime.genres)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatBlock(
                    label = "Сезоны и серии",
                    value = anime.episodesInfo?.split(",")?.firstOrNull()?.trim() ?: "Неизвестно",
                    secondaryValue = anime.episodesInfo?.split(",")?.getOrNull(1)?.trim(),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_playlist),
                            contentDescription = "Серии",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    backgroundColor = Color(0xFFE8F5E9).copy(alpha = 0.7f),
                    modifier = Modifier.weight(1f)
                )

                StatBlock(
                    label = "Рейтинг",
                    value = anime.rating,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Рейтинг",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    backgroundColor = Color(0xFFFFF3E0).copy(alpha = 0.7f),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            anime.description?.let { Description(it) }

            Spacer(modifier = Modifier.height(16.dp))

            anime.ratings?.let {
                Text(
                    text = "Распределение оценок",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 4.dp),
                    color = Color.Black
                )
                RatingBarChart(ratings = it)
            }

            Spacer(modifier = Modifier.height(4.dp))

            anime.recommendations?.let { recommendations ->
                Text(
                    text = "Рекомендации:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 4.dp)
                        .clickable { onRecommendationsClick() },
                    color = Color.Black
                )

                LazyRow(
                    contentPadding = PaddingValues(start = 4.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recommendations) { rec ->
                        AnimeCard(
                            title = rec.title,
                            rating = rec.rating,
                            genres = rec.genres,
                            image = painterResource(id = rec.imageResId),
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { onAnimeClick(rec.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatBlock(
    label: String,
    value: String,
    secondaryValue: String? = null,
    icon: @Composable () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            icon()

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            secondaryValue?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }

            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun AnimeScreenGenreTags(genres: List<String>) {
    val rowWidth = 300.dp
    val genreChunks = mutableListOf<List<String>>()
    var currentRow = mutableListOf<String>()
    var currentWidth = 0.dp

    genres.forEach { genre ->
        val genreWidth = (genre.length * 7.5).dp + 12.dp

        if (currentWidth + genreWidth > rowWidth) {
            genreChunks.add(currentRow)
            currentRow = mutableListOf()
            currentWidth = 0.dp
        }

        currentRow.add(genre)
        currentWidth += genreWidth
    }

    if (currentRow.isNotEmpty()) {
        genreChunks.add(currentRow)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        genreChunks.forEach { rowGenres ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                rowGenres.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(getGenreColor(genre).copy(alpha = 0.2f))
                            .border(
                                width = 1.dp,
                                color = getGenreColor(genre),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = genre,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = getGenreColor(genre)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Description(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        Text(
            text = "Описание",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun AnimeScreen(
    animeId: Int,
    viewModelFactory: AnimeDetailViewModel.Factory,
    onAnimeClick: (Int) -> Unit,
    onRecommendationsClick: () -> Unit
) {
    val viewModel: AnimeDetailViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.state.collectAsState()

    when (state) {
        is AnimeDetailState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AnimeDetailState.Error -> {
            val errorState = state as AnimeDetailState.Error
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(errorState.message)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadAnime() }) {  // Убрали передачу animeId
                        Text("Повторить")
                    }
                }
            }
        }
        is AnimeDetailState.Success -> {
            val successState = state as AnimeDetailState.Success
            AnimeScreenContent(
                anime = successState.anime,
                onAnimeClick = onAnimeClick,
                onRecommendationsClick = onRecommendationsClick
            )
        }
    }
}



