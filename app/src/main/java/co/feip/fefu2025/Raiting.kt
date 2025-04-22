package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingBarChart(
    ratings: Map<Int, Int>,
    modifier: Modifier = Modifier
) {
    val maxCount = ratings.values.maxOrNull() ?: 1
    val maxBarHeight = 160.dp
    val barWidth = 24.dp

    val juicyRed = Color(0xFFEC0905)
    val juicyYellow = Color(0xFFFFC107)
    val juicyGreen = Color(0xFF05B20A)

    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        (1..10).forEach { rating ->
            val count = ratings[rating] ?: 0
            val barHeightRatio = count / maxCount.toFloat()

            val color = when {
                rating <= 5 -> lerp(juicyRed, juicyYellow, (rating - 1) / 4f)
                else -> lerp(juicyYellow, juicyGreen, (rating - 6) / 4f)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = count.toString(),
                    fontSize = 10.sp,
                    color = Color(0xFF888888),
                    modifier = Modifier.padding(bottom = 4.dp) // Уменьшил отступ
                )

                Box(
                    modifier = Modifier
                        .height((maxBarHeight * barHeightRatio).coerceAtLeast(6.dp))
                        .width(barWidth)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(color)
                )

                Text(
                    text = rating.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp) // Уменьшил отступ
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarChartPreview() {
    val sampleData = mapOf(
        1 to 100,
        2 to 50,
        3 to 200,
        4 to 150,
        5 to 300,
        6 to 250,
        7 to 400,
        8 to 350,
        9 to 450,
        10 to 500
    )

    RatingBarChart(ratings = sampleData)
}