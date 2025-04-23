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

    val favoriteIds: StateFlow<Set<Int>> = favoritesController.favoriteIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        fetchTopAnimeList()
    }

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

    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            favoritesController.toggle(anime)
        }
    }
}