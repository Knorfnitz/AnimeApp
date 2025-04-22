package com.example.projekt.data.repository

import com.example.projekt.data.remote.AnimeDto
import com.example.projekt.domain.model.Anime
import com.example.projekt.domain.repository.AnimeRepositoryInterface

object MockRepository: AnimeRepositoryInterface {
    override suspend fun getTopAnime(): List<Anime> {
        return listOf(
            Anime(1, "One Piece", "https://cdn.myanimelist.net/images/anime/6/73245.jpg"),
            Anime(2, "Death Note", "https://cdn.myanimelist.net/images/anime/9/9453.jpg"),
            Anime(3, "Naruto", "https://cdn.myanimelist.net/images/anime/13/17405.jpg")
        )
    }
}