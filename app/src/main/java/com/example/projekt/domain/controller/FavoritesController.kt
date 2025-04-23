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

    val favoriteIds: Flow<Set<Int>> = repo
        .getAllFavorites()
        .map { list -> list.map { it.id }.toSet() }

    val favorites: Flow<List<FavoriteAnimeEntity>> = repo.getAllFavorites()

    suspend fun toggle(anime: Anime) {
        if (repo.isFavorite(anime.id)) {
            repo.removeFromFavorites(anime.toEntity())
        } else {
            repo.addToFavorites(anime.toEntity())
        }
    }

    suspend fun remove(anime: Anime) {
        repo.removeFromFavorites(anime.toEntity())
    }
}