package com.example.projekt.data.remote

import com.example.projekt.domain.model.Anime

fun AnimeDto.toDomain(): Anime {
    return Anime(
        id = malId,
        title = title,
        imageUrl = images.jpg.imageUrl
    )
}