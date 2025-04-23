package com.example.projekt.data.remote

import com.example.projekt.data.local.FavoriteAnimeEntity
import com.example.projekt.domain.model.Anime

fun AnimeDto.toDomain(): Anime {
    return Anime(
        id = malId,
        title = title,
        imageUrl = images.jpg.imageUrl,
        score = score,
        rank = rank,
        episodes = episodes,
        type = type,
        year = year
    )
}

fun FavoriteAnimeEntity.toDomain() = Anime(
    id = id,
    title = title,
    imageUrl = imageUrl,
    score = score,
    rank = rank,
    episodes = episodes,
    type = type,
    year = year
)


fun AnimeDetailDto.toDomain(): Anime {
    return Anime(
        id = malId,
        title = title,
        imageUrl = images.jpg.imageUrl,
        score = score,
        rank = rank,
        episodes = episodes,
        type = type,
        year = year
    )
}