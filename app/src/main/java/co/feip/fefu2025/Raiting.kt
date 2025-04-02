package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AnimeRatingDistribution(
    val totalVotes: Int,
    val ratings: Map<Int, Int>
) {
    init {
        require(ratings.keys.all { it in 1..10 }) {
            "Ratings should contain keys from 1 to 10"
        }
    }
}

@Composable
fun RatingDistributionChart(
    ratingData: AnimeRatingDistribution,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF6200EE),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    maxBarHeight: Dp = 180.dp,
    showValues: Boolean = true
) {
    val maxValue = ratingData.ratings.values.maxOrNull() ?: 1

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(maxBarHeight + 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ratingData.ratings.entries.sortedBy { it.key }.forEach { (rating, count) ->
                val height = if (maxValue > 0) {
                    maxBarHeight * count / maxValue
                } else {
                    0.dp
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(30.dp)
                ) {
                    if (showValues) {
                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(height)
                            .width(20.dp)
                            .background(
                                color = barColor,
                                shape = RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 4.dp
                                )
                            )
                    )

                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

