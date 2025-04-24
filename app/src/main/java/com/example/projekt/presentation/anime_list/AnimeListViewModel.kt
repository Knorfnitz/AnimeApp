package com.example.projekt.presentation.anime_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekt.domain.controller.FavoritesController
import com.example.projekt.domain.model.Anime
import com.example.projekt.domain.repository.AnimeRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AnimeListViewModel(
    private val animeRepo: AnimeRepositoryInterface,
    private val favoritesController: FavoritesController
) : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList = _animeList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    /**
     * StateFlow mit den IDs der favorisierten Animes.
     */
    val favoriteIds: StateFlow<Set<Int>> = favoritesController.favoriteIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        fetchTopAnimeList()
    }

    /**
     * Lädt die Liste der Top Anime von der API.
     *
     * Setzt während des Ladevorgangs den Ladezustand und behandelt mögliche Fehlerquellen wie
     * Netzwerkprobleme oder Serverfehler.
     */
    fun fetchTopAnimeList() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = runCatching { animeRepo.getTopAnime() }

            result
                .onSuccess { list -> _animeList.value = list }
                .onFailure { throwable ->
                    _error.value = when (throwable) {
                        is IOException -> "Bitte überprüfe deine Internetverbindung"
                        is HttpException -> "Serverfehler (${throwable.code()})"
                        else -> "Unbekannter Fehler: ${throwable.message}"
                    }
                    Log.e("AnimeListVM", "Fehler beim Laden", throwable)
                }

            _isLoading.value = false
        }
    }

    /**
     * Fügt einen Anime zu den Favoriten hinzu oder entfernt ihn, falls er bereits favorisiert wurde.
     *
     * Die Änderung wird im FavoritesController gespeichert.
     *
     * @param anime Der Anime, dessen Favoritenstatus geändert werden soll.
     */
    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            favoritesController.toggle(anime)
        }
    }
}