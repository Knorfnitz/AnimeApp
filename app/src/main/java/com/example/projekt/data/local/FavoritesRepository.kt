package com.example.projekt.data.local

import com.example.projekt.domain.repository.FavoritesRepositoryInterface
import kotlinx.coroutines.flow.Flow


class FavoritesRepository(
    private val dao: FavoriteAnimeDao
) : FavoritesRepositoryInterface {

    override fun getAllFavorites(): Flow<List<FavoriteAnimeEntity>> {
        return dao.getAll()
    }

    override suspend fun addToFavorites(anime: FavoriteAnimeEntity) {
        dao.insert(anime)
    }

    override suspend fun removeFromFavorites(anime: FavoriteAnimeEntity) {
        dao.delete(anime)
    }

    override suspend fun isFavorite(animeId: Int): Boolean {
        return dao.isFavorite(animeId)
    }
}