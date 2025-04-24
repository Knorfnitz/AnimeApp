package com.example.projekt.presentation.anime_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projekt.data.repository.MockRepository
import com.example.projekt.domain.model.Anime
import com.example.projekt.presentation.component.AnimeCard
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel = koinViewModel(),
    onNavigateToDetail: (Anime) -> Unit,
   // onBack: () -> Unit,

) {
    val animeList = viewModel.animeList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()
    val favorites = viewModel.favoriteIds.collectAsState()

    AnimeListContent(
        animeList = animeList.value,
        isLoading = isLoading.value,
        error = error.value,
        onRetry = { viewModel.fetchTopAnimeList() },
        favoriteIds = favorites.value,
        onFavoriteClick = { viewModel.toggleFavorite(it) },
        onNavigateToDetail = onNavigateToDetail,
       // onBack = onBack,
    )
}

@Composable
fun AnimeListContent(
    animeList: List<Anime>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    favoriteIds: Set<Int>,
    onFavoriteClick: (Anime) -> Unit,
    onNavigateToDetail: (Anime) -> Unit,
   // onBack: () -> Unit
) {


    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetry) {
                        Text("Erneut versuchen")
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(animeList) { anime ->
                        AnimeCard(
                            anime = anime,
                            isFavorite = favoriteIds.contains(anime.id),
                            onFavoriteClick = onFavoriteClick,
                            onClick = {
                                onNavigateToDetail(anime)
                            },
                          //  onBack = onBack
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeListScreenPreview() {
    val mockList = runBlocking {
        MockRepository.getTopAnime()
    }

    MaterialTheme {
        AnimeListContent(
            animeList = mockList,
            isLoading = false,
            error = null,
            onRetry = {},
            favoriteIds = emptySet(),
            onFavoriteClick = {},
            onNavigateToDetail = { }
        )
    }
}