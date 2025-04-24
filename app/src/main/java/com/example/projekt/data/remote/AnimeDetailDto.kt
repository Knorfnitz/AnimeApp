package com.example.projekt.data.remote

import com.squareup.moshi.Json

data class AnimeDetailDto(
    @Json(name = "mal_id") val id: Int,
    val title: String,
    @Json(name = "title_english") val titleEnglish: String?,
    @Json(name = "title_japanese") val titleJapanese: String?,
    @Json(name = "images") val images: ImageWrapper,
    val score: Double?,
    val rank: Int?,
    val episodes: Int?,
    val status: String?,
    val type: String?,
    val year: Int?,
    val rating: String?,
    val trailer: TrailerDto?
)

data class TrailerDto(
    @Json(name = "embed_url") val embedUrl: String?,
    @Json(name = "images") val images: TrailerImageDto?
)
data class TrailerImageDto(
    @Json(name = "maximum_image_url") val maxRes: String?
)