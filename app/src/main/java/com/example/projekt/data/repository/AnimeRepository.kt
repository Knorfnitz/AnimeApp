package com.example.projekt.data.repository

import com.example.projekt.data.remote.AnimeDto
import com.example.projekt.data.remote.JikanService
import com.example.projekt.data.remote.toDomain
import com.example.projekt.domain.model.Anime
import com.example.projekt.domain.repository.AnimeRepositoryInterface

class AnimeRepository(
    private val api: JikanService
) : AnimeRepositoryInterface {
    override suspend fun getTopAnime(): List<Anime> {
        return api.getTopAnime().data.map { it.toDomain() }
    }
}