package com.example.projekt.presentation.anime_detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekt.data.remote.JikanService
import com.example.projekt.data.remote.toDomain
import com.example.projekt.domain.model.AnimeDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AnimeDetailViewModel(
    private val jikanService: JikanService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = savedStateHandle["id"]
        ?: throw IllegalStateException("ID was not found in SavedStateHandle")

    private val _anime = MutableStateFlow<AnimeDetail?>(null)
    val anime: StateFlow<AnimeDetail?> = _anime

    private val _error = MutableStateFlow<String?>(null)

    init {
        fetchAnimeDetail()
    }

    /**
     * Lädt die Detailinformationen des Animes mit der gegebenen ID von der Jikan API.
     *
     * Die geladenen Daten werden in [_anime] gespeichert. Im Fehlerfall wird [_error] gesetzt.
     */
    private fun fetchAnimeDetail() {
        viewModelScope.launch {
            runCatching {
                jikanService.getAnimeById(id).data.toDomain()
            }.onSuccess {
                _anime.value = it
            }.onFailure { throwable ->
                _error.value = when (throwable) {
                    is IOException -> "Bitte überprüfe deine Internetverbindung."
                    is HttpException -> "Serverfehler (${throwable.code()})"
                    else -> "Unbekannter Fehler: ${throwable.localizedMessage}"
                }
            }
        }
    }
}