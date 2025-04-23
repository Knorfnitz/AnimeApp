package com.example.projekt.data.repository

import com.example.projekt.domain.model.Anime
import com.example.projekt.domain.repository.AnimeRepositoryInterface

object MockRepository: AnimeRepositoryInterface {
    override suspend fun getTopAnime(): List<Anime> {
        return listOf(
            Anime(
                id = 1,
                title = "One Piece",
                imageUrl = "https://cdn.myanimelist.net/images/anime/6/73245.jpg",
                score = 8.6,
                rank = 50,
                episodes = 1000,
                type = "TV",
                year = 1999
            ),
            Anime(
                id = 2,
                title = "Death Note",
                imageUrl = "https://cdn.myanimelist.net/images/anime/9/9453.jpg",
                score = 9.0,
                rank = 1,
                episodes = 37,
                type = "TV",
                year = 2006
            )
        )
    }
}