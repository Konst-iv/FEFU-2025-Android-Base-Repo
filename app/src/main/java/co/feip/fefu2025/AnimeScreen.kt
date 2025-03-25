package co.feip.fefu2025

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
fun AnimeDetailScreen(
    image: Painter,
    title: String,
    genres: List<Pair<String, Color>>,
    description: String,
    rating: String,
    year: String,
    episodes: String
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
                .height(450.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenreTags(genres = genres)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(icon = Icons.Default.Star, text = rating)
            InfoItem(icon = Icons.Default.DateRange, text = year)
            InfoItem(icon = Icons.Default.List, text = episodes)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Описание
        Text(
            text = "Описание",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp
            )
        )
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
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text)
    }
}

// Функция
fun generateGenreColors(genres: List<String>): List<Pair<String, Color>> {
    val colors = listOf(
        Color(0xFFF44336),
        Color(0xFF2196F3),
        Color(0xFF4CAF50),
        Color(0xFF9C27B0),
        Color(0xFFFF9800),
        Color(0xFF607D8B)
    )

    return genres.mapIndexed { index, genre ->
        genre to colors[index % colors.size]
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeDetailScreenPreview() {
    MaterialTheme {
        AnimeDetailScreen(
            image = painterResource(id = R.drawable.cowboy_beebop),
            title = "Cowboy Bebop",
            genres = generateGenreColors(listOf("Action", "Drama", "Fantasy", "Horror", "Sci-Fi")),
            description = "История команды охотников за головами в космосе...",
            rating = "8.9",
            year = "1998",
            episodes = "26"
        )
    }
}