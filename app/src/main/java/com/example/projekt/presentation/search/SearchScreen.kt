package com.example.projekt.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projekt.domain.model.Anime
import com.example.projekt.presentation.anime_list.component.AnimeCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onNavigateToDetail: (Anime) -> Unit
) {
    val query = remember { mutableStateOf("") }
    val results = viewModel.results.collectAsState()
    val favorites = viewModel.favoriteIds.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        OutlinedTextField(
            value = query.value,
            onValueChange = {
                query.value = it
                viewModel.updateQuery(it)
            },
            label = { Text("Anime suchen") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.updateQuery(query.value)
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading.value -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error.value != null -> {
                Text(
                    text = error.value ?: "Fehler bei der Suche",
                    color = MaterialTheme.colorScheme.error
                )
            }

            results.value.isEmpty() && query.value.isNotBlank() -> {
                Text("Keine Ergebnisse für \"${query.value}\"")
            }

            else -> {
                LazyColumn {
                    items(results.value) { anime ->
                        AnimeCard(
                            anime = anime,
                            isFavorite = favorites.value.contains(anime.id),
                            onFavoriteClick = { viewModel.toggleFavorite(anime) },
                            onClick = {
                                onNavigateToDetail(anime)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    // Use Theme here
    SearchScreen(
        onNavigateToDetail = { }
    )
}