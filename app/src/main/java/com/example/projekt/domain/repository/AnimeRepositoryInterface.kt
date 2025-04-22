package com.example.projekt.domain.repository

import com.example.projekt.domain.model.Anime

interface AnimeRepositoryInterface {
    suspend fun getTopAnime(): List<Anime>
}