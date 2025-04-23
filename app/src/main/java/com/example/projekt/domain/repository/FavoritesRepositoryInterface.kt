package com.example.projekt.domain.repository

import com.example.projekt.data.local.FavoriteAnimeEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepositoryInterface {
    fun getAllFavorites(): Flow<List<FavoriteAnimeEntity>>
    suspend fun addToFavorites(anime: FavoriteAnimeEntity)
    suspend fun removeFromFavorites(anime: FavoriteAnimeEntity)
    suspend fun isFavorite(animeId: Int): Boolean
}