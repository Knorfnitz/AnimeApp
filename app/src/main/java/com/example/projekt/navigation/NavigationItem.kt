package com.example.projekt.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationItem(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    TopAnimeList(
        route = AnimeListScreenRoute,
        label ="Top Anime",
        icon = Icons.Default.AutoStories
    ),
    Search(
        route = SearchScreenRoute,
        label = "Suche",
        icon = Icons.Default.Search
    ),

    Favorites(
        route = FavoritesScreenRoute,
        label = "Favoriten",
        icon = Icons.Default.Favorite
    ),


}