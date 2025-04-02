package co.feip.fefu2025

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.util.TypedValueCompat.dpToPx

import co.feip.fefu2025.RatingDistributionChart



data class AnimeRecommendation(
    val id: Int,
    val image: Painter,
    val title: String,
    val genres: List<Pair<String, Color>>,
    val rating: String
)

@Composable
fun AnimeDetailScreen(
    image: Painter,
    title: String,
    genres: List<Pair<String, Color>>,
    description: String,
    rating: String,
    year: String,
    episodes: String,
    ratingDistribution: AnimeRatingDistribution? = null,
    recommendations: List<AnimeRecommendation> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(470.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(16.dp))

        GenreTags(genres = genres)

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(Icons.Default.Star, rating)
            InfoItem(Icons.Default.DateRange, year)
            InfoItem(Icons.Default.List, episodes)
        }

        ratingDistribution?.let {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Рейтинг аниме",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(8.dp))
            RatingDistributionChart(it)
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Описание",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        if (recommendations.isNotEmpty()) {
            Spacer(Modifier.height(46.dp))
            Text(
                text = "Рекомендации",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                items(recommendations) { item ->
                    AnimeCard(
                        image = item.image,
                        title = item.title,
                        genres = item.genres,
                        rating = item.rating,
                        modifier = Modifier.width(200.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun GenreTags(genres: List<Pair<String, Color>>) {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            FlexBoxLayout(context).apply {
                itemHorizontalGap = dpToPx(8, context)
                itemVerticalGap = dpToPx(8, context)
            }
        },
        update = { flexBox ->
            flexBox.removeAllViews()
            genres.forEach { (genre, color) ->
                val view = LayoutInflater.from(context).inflate(
                    R.layout.genre_view,
                    flexBox,
                    false
                ).apply {
                    findViewById<TextView>(R.id.tvGenreName).text = genre
                    background = createGenreBackground(color.toArgb())
                }
                flexBox.addView(view)
            }
        }
    )
}

private fun createGenreBackground(color: Int): Drawable {
    return GradientDrawable().apply {
        cornerRadius = 16f
        setColor(color)
    }
}

private fun Color.toArgb(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}

private fun dpToPx(dp: Int, context: Context): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

@Composable
private fun InfoItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeDetailScreenPreview() {
    MaterialTheme {
        AnimeDetailScreen(
            image = painterResource(R.drawable.cowboy_beebop),
            title = "Cowboy Bebop",
            genres = listOf("Action", "Sci-Fi", "Adventure")
                .mapIndexed { i, genre -> genre to getGenreColor(i) },
            description = "Классика аниме про космических охотников...",
            rating = "8.9",
            year = "1998",
            episodes = "26",
            ratingDistribution = AnimeRatingDistribution(
                totalVotes = 1000,
                ratings = mapOf(
                    1 to 10,
                    2 to 20,
                    3 to 30,
                    4 to 40,
                    5 to 50,
                    6 to 60,
                    7 to 70,
                    8 to 80,
                    9 to 90,
                    10 to 100
                )
            ),
            recommendations = listOf(
                AnimeRecommendation(
                    id = 1,
                    image = painterResource(R.drawable.cowboy_beebop),
                    title = "Attack on Titan",
                    genres = listOf("Action", "Drama").mapIndexed { i, genre -> genre to getGenreColor(i) },
                    rating = "9.5"
                ),
                AnimeRecommendation(
                    id = 2,
                    image = painterResource(R.drawable.cowboy_beebop),
                    title = "My Hero Academia",
                    genres = listOf("Action", "Adventure").mapIndexed { i, genre -> genre to getGenreColor(i) },
                    rating = "8.3"
                ),
                AnimeRecommendation(
                    id = 3,
                    image = painterResource(R.drawable.cowboy_beebop),
                    title = "Demon Slayer",
                    genres = listOf("Action", "Fantasy").mapIndexed { i, genre -> genre to getGenreColor(i) },
                    rating = "9.0"
                ),
                AnimeRecommendation(
                    id = 4,
                    image = painterResource(R.drawable.cowboy_beebop),
                    title = "Death Note",
                    genres = listOf("Mystery", "Thriller").mapIndexed { i, genre -> genre to getGenreColor(i) },
                    rating = "9.0"
                ),
                AnimeRecommendation(
                    id = 5,
                    image = painterResource(R.drawable.cowboy_beebop),
                    title = "Fullmetal Alchemist: Brotherhood",
                    genres = listOf("Action", "Adventure").mapIndexed { i, genre -> genre to getGenreColor(i) },
                    rating = "9.2"
                )
            )
        )
    }
}

private fun getGenreColor(index: Int): Color {
    val colors = listOf(
        Color(0xFFF44336),
        Color(0xFF2196F3),
        Color(0xFF4CAF50)
    )
    return colors[index % colors.size]
}