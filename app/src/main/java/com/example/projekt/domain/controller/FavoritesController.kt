package com.example.projekt.domain.controller

import com.example.projekt.data.local.FavoriteAnimeEntity
import com.example.projekt.domain.model.Anime
import com.example.projekt.domain.model.toEntity
import com.example.projekt.domain.repository.FavoritesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesController(
    private val repo: FavoritesRepositoryInterface
) {

    /**
     * Gibt eine Flow-Liste aller Favoriten-IDs zurück.
     *
     * Diese wird z. B. genutzt, um in der UI anzuzeigen, ob ein Anime bereits favorisiert ist.
     */
    val favoriteIds: Flow<Set<Int>> = repo
        .getAllFavorites()
        .map { list -> list.map { it.id }.toSet() }

    /**
     * Gibt die vollständige Flow-Liste aller gespeicherten Favoriten als Entities zurück.
     *
     * Kann verwendet werden, um die komplette Favoritenliste in der UI anzuzeigen.
     */
    val favorites: Flow<List<FavoriteAnimeEntity>> = repo.getAllFavorites()

    /**
     * Fügt den Anime zu den Favoriten hinzu oder entfernt ihn, wenn er bereits ein Favorit ist.
     *
     * Diese Funktion prüft zunächst, ob der Anime bereits gespeichert ist.
     * Entsprechend wird `addToFavorites` oder `removeFromFavorites` aufgerufen.
     *
     * @param anime Der Anime, dessen Favoritenstatus umgeschaltet werden soll.
     */
    suspend fun toggle(anime: Anime) {
        if (repo.isFavorite(anime.id)) {
            repo.removeFromFavorites(anime.toEntity())
        } else {
            repo.addToFavorites(anime.toEntity())
        }
    }

    /**
     * Entfernt den angegebenen Anime explizit aus den Favoriten.
     *
     * @param anime Der zu entfernende Anime.
     */
    suspend fun remove(anime: Anime) {
        repo.removeFromFavorites(anime.toEntity())
    }
}