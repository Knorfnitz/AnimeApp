package com.example.projekt.presentation.anime_detail_screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.projekt.domain.model.AnimeDetail
import com.example.projekt.presentation.component.StarRating


@Composable
fun AnimeDetailScreen(
    anime: AnimeDetail,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val detailTextStyle = MaterialTheme.typography.bodyLarge.copy(
        lineHeight = 24.sp
    )


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
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = anime.title, style = MaterialTheme.typography.headlineSmall)

                anime.titleEnglish?.let {
                    Text(text = "Englisch: $it", style = MaterialTheme.typography.bodyMedium)
                }

                anime.titleJapanese?.let {
                    Text(text = "Japanisch: $it", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    anime.score?.let {
                        StarRating(score = it)
                    }
                    anime.score?.let {
                        Text("   $it", style = MaterialTheme.typography.titleMedium)
                    }

                }

                anime.episodes?.let {
                    Text(text = "Episoden: $it", style = detailTextStyle)
                }

                anime.year?.let {
                    Text(text = "Jahr: $it", style = detailTextStyle)
                }

                anime.status?.let {
                    Text(text = "Status: $it", style = detailTextStyle)
                }

                anime.rating?.let {
                    Text(text = "FSK: $it", style = detailTextStyle)
                }

                anime.type?.let {
                    Text(text = "Typ: $it", style = detailTextStyle)
                }

                Spacer(modifier = Modifier.height(16.dp))

                anime.trailerUrl?.let { trailerUrl ->
                    val context = LocalContext.current
                    val thumbnail = anime.trailerThumbnail ?: return@let

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Trailer", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, trailerUrl.toUri())
                                context.startActivity(intent)
                            }
                    ) {
                        AsyncImage(
                            model = thumbnail,
                            contentDescription = "Trailer Preview",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )

                        Icon(
                            imageVector = Icons.Default.PlayCircleFilled,
                            contentDescription = "Play",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(64.dp),
                            tint = Color.White.copy(alpha = 0.85f)
                        )
                    }
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
    val mock = AnimeDetail(
        id = 1,
        title = "Fullmetal Alchemist: Brotherhood",
        titleEnglish = "Fullmetal Alchemist: Brotherhood",
        titleJapanese = "鋼の錬金術師",
        imageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg",
        score = 9.1,
        rank = 1,
        episodes = 64,
        status = "Finished Airing",
        type = "TV",
        year = 2009,
        rating = "R - 17+ (violence & profanity)",
        trailerUrl = "https://www.youtube.com/embed/1ac3_YdSSy0?enablejsapi=1",
        trailerThumbnail = "https://img.youtube.com/vi/1ac3_YdSSy0/hqdefault.jpg"
    )

    MaterialTheme {
        AnimeDetailScreen(
            anime = mock,
            onBack = {}
        )
    }
}