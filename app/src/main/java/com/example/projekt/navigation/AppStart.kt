package com.example.projekt.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projekt.R
import com.example.projekt.presentation.anime_list.AnimeListScreen
import com.example.projekt.presentation.favorite.FavoriteScreen
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppStart() {

    val navController = rememberNavController()
    var selectedNavigationItem by rememberSaveable { mutableStateOf(NavigationItem.VeggieList) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Otaku")
                        Spacer(Modifier.weight(1f))
                    }
                },
            )
        },
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
                AnimeListScreen()
            }
            composable<FavoritesScreenRoute> {
                FavoriteScreen()
            }
        }
    }
}
