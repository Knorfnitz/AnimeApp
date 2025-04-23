package com.example.projekt.domain.model

import com.example.projekt.data.local.FavoriteAnimeEntity

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Double?,
    val rank: Int?,
    val episodes: Int?,
    val type: String?,
    val year: Int?
)

fun Anime.toEntity() = FavoriteAnimeEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    score = score,
    rank = rank,
    episodes = episodes,
    type = type,
    year = year
)
