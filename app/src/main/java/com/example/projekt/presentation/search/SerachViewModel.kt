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
    fun updateQuery(input: String) {
        _query.value = input
    }

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

    private suspend fun search(query: String) {
        _isLoading.value = true
        _error.value = null
        val result = runCatching { api.searchAnime(query).data.map { it.toDomain() } }

        result
            .onSuccess { _results.value = it }
            .onFailure { _error.value = it.message }

        _isLoading.value = false
    }

    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            favoritesController.toggle(anime)
        }
    }
}