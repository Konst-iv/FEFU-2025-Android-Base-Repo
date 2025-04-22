package co.feip.fefu2025.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun getGenreColor(genre: String): Color {
    return when (genre.toLowerCase()) {
        "экшен" -> Color(0xFFFF5252)
        "приключения" -> Color(0xFFFF9800)
        "драма" -> Color(0xFF2196F3)
        "фэнтези" -> Color(0xFF4CAF50)
        "сёнен" -> Color(0xFF9C27B0)
        "фантастика" -> Color(0xFF00BCD4)
        "нуар" -> Color(0xFF607D8B)
        "романтика" -> Color(0xFFE91E63)
        "повседневность" -> Color(0xFF795548)
        "ужасы" -> Color(0xFF673AB7)
        "психологическое" -> Color(0xFF3F51B5)
        "триллер" -> Color(0xFFF44336)
        "детектив" -> Color(0xFF009688)
        "сверхъестественное" -> Color(0xFF8BC34A)
        "политика" -> Color(0xFFCDDC39)
        "меха" -> Color(0xFF9E9E9E)
        "школа" -> Color(0xFFFFC107)
        "комедия" -> Color(0xFFFFEB3B)
        "история" -> Color(0xFF795548)
        "медицина" -> Color(0xFFE57373)
        else -> Color(0xFFE0E0E0) // цвет по умолчанию
    }
}