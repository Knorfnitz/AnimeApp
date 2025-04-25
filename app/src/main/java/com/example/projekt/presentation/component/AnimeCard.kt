package com.example.projekt.presentation.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.projekt.domain.model.Anime

@Composable
fun AnimeCard(
    anime: Anime,
    isFavorite: Boolean,
    onFavoriteClick: (Anime) -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                onLoading = { Log.d("AsyncImage", "Image loading: ${anime.imageUrl}") },
                onError = { Log.e("AsyncImage", "Error loading image: ${anime.imageUrl}") },
                onSuccess = { Log.d("AsyncImage", "Image loaded successfully: ${anime.imageUrl}") },
                modifier = Modifier
                    .height(200.dp)
                    .aspectRatio(2f / 3f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    anime.type?.let {
                        Text(
                            "Typ: $it",
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall)

                    }
                    anime.year?.let {
                        Text(
                            "Jahr: $it",
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall)
                    }
                    anime.episodes?.let {
                        Text(
                            "Episoden: $it",
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall)
                    }
                    anime.rank?.let {
                        Text(
                            "Rang:   $it",
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = MaterialTheme.typography.titleMedium)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    anime.score?.let {
                        StarRating(score = it)
                    }
                    anime.score?.let {
                        Text("   $it", style = MaterialTheme.typography.titleMedium)
                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ){

                    IconButton(onClick = { onFavoriteClick(anime) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorit",
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeCardPreview() {
    val sample = Anime(
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
        AnimeCard(
            anime = sample,
            isFavorite = true,
            onFavoriteClick = {},
            //onBack = { }
        )
    }
}