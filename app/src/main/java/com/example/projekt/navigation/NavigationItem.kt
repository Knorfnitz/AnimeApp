package com.example.projekt.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.projekt.R

enum class NavigationItem(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    VeggieList(
        route = AnimeListScreenRoute,
        label ="Top Anime",
        icon = Icons.Default.AutoStories
    ),
    Favorites(
        route = FavoritesScreenRoute,
        label = "Favoriten",
        icon = Icons.Default.Favorite
    )
}