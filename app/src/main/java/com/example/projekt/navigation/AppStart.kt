package com.example.projekt.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekt.presentation.anime_detail_screen.AnimeDetailScreen
import com.example.projekt.presentation.anime_detail_screen.AnimeDetailViewModel
import com.example.projekt.presentation.anime_list.AnimeListScreen
import com.example.projekt.presentation.favorite.FavoriteScreen
import com.example.projekt.presentation.search.SearchScreen
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppStart() {

    val navController = rememberNavController()
    var selectedNavigationItem by rememberSaveable { mutableStateOf(NavigationItem.TopAnimeList) }

    Scaffold(
      /*  topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Otaku")
                        Spacer(Modifier.weight(1f))
                    }
                },
            )
        },*/
        bottomBar = {
            BottomNavigationBar(
                navItems = NavigationItem.entries,
                onNavItemSelection = {
                    selectedNavigationItem = it
                },
                selectedNavItem = selectedNavigationItem,
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = selectedNavigationItem.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<AnimeListScreenRoute> {
                AnimeListScreen(
                    onNavigateToDetail = { anime ->
                        navController.navigate(
                            AnimeDetailScreenRoute(
                                id = anime.id,
                            )
                        )
                    }
                )
            }
            composable<FavoritesScreenRoute> {
                FavoriteScreen(
                    onNavigateToDetail = { anime ->
                        navController.navigate(
                            AnimeDetailScreenRoute(
                                id = anime.id,
                            )
                        )
                    }
                )
            }
            composable<SearchScreenRoute> {
                SearchScreen(
                    onNavigateToDetail = { anime ->
                        navController.navigate(
                            AnimeDetailScreenRoute(
                                id = anime.id,
                            )
                        )
                    }
                )
            }
            composable<AnimeDetailScreenRoute> {
                val viewModel: AnimeDetailViewModel = koinViewModel()
                val anime by viewModel.anime.collectAsState()

                anime?.let {
                    AnimeDetailScreen(
                        anime = it,
                         onBack = { navController.popBackStack() }
                    )
                } ?: Text("Lade Anime...")
            }
        }
    }
}
