package com.example.projekt.presentation.anime_detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.example.projekt.domain.model.Anime


@Composable
fun AnimeDetailScreen(
    anime: Anime,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(-1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = anime.title, style = MaterialTheme.typography.headlineSmall)

                anime.score?.let {
                    Text(text = "Score: $it", style = MaterialTheme.typography.bodyMedium)
                }

                anime.episodes?.let {
                    Text(text = "Episoden: $it", style = MaterialTheme.typography.bodyMedium)
                }

                anime.year?.let {
                    Text(text = "Jahr: $it", style = MaterialTheme.typography.bodyMedium)
                }

                anime.type?.let {
                    Text(text = "Typ: $it", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        
        FloatingActionButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "Zurück"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeDetailScreenPreview() {
    val mock = Anime(
        id = 1,
        title = "Fullmetal Alchemist: Brotherhood",
        imageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg",
        score = 9.1,
        rank = 1,
        episodes = 64,
        type = "TV",
        year = 2009
    )

    MaterialTheme {
        AnimeDetailScreen(
            anime = mock,
            onBack = { }
        )
    }
}