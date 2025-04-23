package com.example.projekt.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projekt.domain.model.Anime
import com.example.projekt.presentation.anime_list.component.AnimeCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    onNavigateToDetail: (Anime) -> Unit
) {
    val favorites = viewModel.favorites.collectAsState()


    if (favorites.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Noch keine Favoriten")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(favorites.value) { anime ->
                AnimeCard(
                    anime = anime,
                    isFavorite = true,
                    onFavoriteClick = { viewModel.removeFavorite(anime) },
                    onClick = {
                        onNavigateToDetail(anime)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    // Use Theme here
    FavoriteScreen(
        onNavigateToDetail = {  }
    )
}