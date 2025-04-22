package com.example.projekt.presentation.anime_list.component

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.projekt.domain.model.Anime

@Composable
fun AnimeCard(anime: Anime, modifier: Modifier = Modifier) {

    Log.d("AnimeCard", "Image URL: ${anime.imageUrl}")

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                onLoading = {
                    Log.d("AsyncImage", "Image loading")
                },
                onError = { state ->
                    Log.e("AsyncImage", "Error loading image")
                },
                onSuccess = { state ->
                    Log.d("AsyncImage", "Image loaded successfully")
                },
                modifier = Modifier
                    .height(200.dp)
                    .aspectRatio(2f / 3f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = anime.title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeCardPreview() {
    val sample = Anime(
        id = 123,
        title = "Fullmetal Alchemist: Brotherhood",
        imageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg"
    )

    MaterialTheme {
        AnimeCard(anime = sample)
    }
}