package com.example.projekt.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(score: Double, modifier: Modifier = Modifier) {
    val maxStars = 5
    val starsValue = (score / 2.0).coerceIn(0.0, maxStars.toDouble())

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(maxStars) { index ->
            val fillPercent = when {
                index + 1 <= starsValue -> 1f
                index.toDouble() < starsValue -> (starsValue - index).toFloat()
                else -> 0f
            }

            Box(modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Filled.StarBorder,
                    contentDescription = null,
                   // tint = Color(0xFFFFD700),
                    tint = Color.Gray,
                    modifier = Modifier.matchParentSize()
                )

                if (fillPercent > 0f) {

                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RectangleShape)
                            .drawWithContent {
                                val width = size.width * fillPercent
                                clipRect(right = width) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun StarRatingPreview() {
    // Use Theme here
    StarRating(
        score = 7.0
    )
}