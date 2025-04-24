package com.example.projekt.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekt.data.local.FavoriteAnimeEntity
import com.example.projekt.data.remote.toDomain
import com.example.projekt.domain.controller.FavoritesController
import com.example.projekt.domain.model.Anime
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesController: FavoritesController
) : ViewModel() {

    val favorites: StateFlow<List<Anime>> = favoritesController.favorites
        .map { list: List<FavoriteAnimeEntity> ->
            list.map { it.toDomain() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Entfernt den angegebenen Anime aus der Favoritenliste.
     *
     * Diese Funktion wird im Hintergrund über eine Coroutine ausgeführt und
     * delegiert den Löschvorgang an den [FavoritesController].
     *
     * @param anime Der Anime, der aus den Favoriten entfernt werden soll.
     */
    fun removeFavorite(anime: Anime) {
        viewModelScope.launch {
            favoritesController.remove(anime)
        }
    }
}