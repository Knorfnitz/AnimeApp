package com.example.projekt.data.remote

import com.squareup.moshi.Json

data class AnimeDetailDto(
    @Json(name = "mal_id") val malId: Int,
    val title: String,
    @Json(name = "images") val images: ImageWrapper,
    val synopsis: String?,
    val score: Double?,
    val rank: Int?,
    val episodes: Int?,
    val type: String?,
    val year: Int?
)