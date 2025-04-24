package com.example.projekt.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekt.data.remote.JikanService
import com.example.projekt.data.remote.toDomain
import com.example.projekt.domain.controller.FavoritesController
import com.example.projekt.domain.model.Anime
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val api: JikanService,
    private val favoritesController: FavoritesController
) : ViewModel() {

    private val _query = MutableStateFlow("")

    private val _results = MutableStateFlow<List<Anime>>(emptyList())
    val results = _results.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val favoriteIds: StateFlow<Set<Int>> = favoritesController.favoriteIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        viewModelScope.launch {
            _query
                .debounce(300)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { query ->
                    search(query)
                }
        }
    }

    /**
     * Aktualisiert die aktuelle Suchanfrage.
     *
     * Diese Funktion wird verwendet, um die Benutzereingabe in das Suchfeld zu speichern.
     * Die eigentliche Suche wird durch ein debounce-gesteuertes Flow verarbeitet.
     *
     * @param input Der neue Suchbegriff.
     */
    fun updateQuery(input: String) {
        _query.value = input
    }

    /**
     * Führt eine Anime-Suche mit dem angegebenen Suchbegriff durch.
     *
     * Diese Funktion wird intern von einem debouncten Flow aufgerufen,
     * sobald sich die Suchanfrage ändert. Die Ergebnisse werden aus der API geladen,
     * in die Domain-Modelle konvertiert und gespeichert.
     *
     * @param query Der Suchbegriff, nach dem in der Jikan-API gesucht wird.
     */
    private suspend fun search(query: String) {
        _isLoading.value = true
        _error.value = null
        val result = runCatching { api.searchAnime(query).data.map { it.toDomain() } }

        result
            .onSuccess { _results.value = it }
            .onFailure { _error.value = it.message }

        _isLoading.value = false
    }

    /**
     * Fügt den angegebenen Anime den Favoriten hinzu oder entfernt ihn,
     * je nachdem, ob er bereits enthalten ist.
     *
     * Die Umschaltung erfolgt über den [FavoritesController].
     *
     * @param anime Der Anime, dessen Favoritenstatus geändert werden soll.
     */
    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            favoritesController.toggle(anime)
        }
    }
}