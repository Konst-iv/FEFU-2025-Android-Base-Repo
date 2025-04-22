package co.feip.fefu2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import co.feip.fefu2025.ui.theme.getGenreColor

@Composable
fun AnimeCard(
    title: String,
    rating: String,
    genres: List<String>,
    image: Painter,
    modifier: Modifier = Modifier
) {
    val titleFontSize = when {
        title.length <= 10 -> 16.sp
        title.length <= 15 -> 14.sp
        title.length <= 20 -> 12.sp
        else -> 10.sp
    }

    Card(
        modifier = modifier
            .width(200.dp)
            .height(280.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = image,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = title,
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Genres
            GenreTags(genres = genres.take(6))

            Spacer(modifier = Modifier.height(8.dp))

            // Rating
            RatingDisplay(rating = rating) // Исправлено: вызов функции RatingDisplay
        }
    }
}

@Composable
private fun RatingDisplay(rating: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth() // Добавлено: чтобы Row занимал всю ширину
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
            tint = Color(0xFFFFA000),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = rating,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFA000)
        )
    }
}

@Composable
private fun GenreTags(genres: List<String>) {
    val maxTagsPerLine = 3
    val lines = genres.chunked(maxTagsPerLine).take(2) // Максимум 2 строки

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        lines.forEach { lineGenres ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                lineGenres.forEach { genre ->
                    GenreTag(text = genre)
                }
            }
        }
    }
}

@Composable
private fun GenreTag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(getGenreColor(text).copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = getGenreColor(text),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = getGenreColor(text),
            maxLines = 1
        )
    }
}

