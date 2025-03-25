package co.feip.fefu2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
@Composable
fun GenreChip(genre: String, color: Color) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 6.dp) // Уменьшенные отступы
            .background(color, RoundedCornerShape(8.dp)) // Фон с закругленными краями
            .padding(horizontal = 4.5.dp, vertical = 7.dp), // Уменьшенные внутренние отступы
        contentAlignment = Alignment.Center // Центрирование текста
    ) {
        Text(
            text = genre,
            fontSize = 8.sp, // Уменьшенный размер шрифта
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary // Цвет текста
        )
    }
}

@Composable
fun AnimeCard(
    image: Painter,
    title: String,
    genres: List<Pair<String, Color>>,
    rating: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(370.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = image,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                )
            }


            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally), // Центрирование названия
                    maxLines = 1
                )

               
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp) // Расстояние между жанрами
                ) {
                    genres.forEach { (genre, color) ->
                        GenreChip(genre = genre, color = color)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star), // Ваш SVG файл
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = rating,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun AnimeCardPreview() {
    AnimeCard(
        image = painterResource(id = R.drawable.cowboy_beebop), // Замените на ваш ресурс
        title = "Cowboy Bebop",
        genres = listOf(
            "Боевик" to Color.Blue,
            "Приключения" to Color.Green,
            "Фантастика" to Color.Red,

        ),
        rating = "9.7"
    )
}