package com.example.projekt.data.remote

import com.squareup.moshi.Json

// dto = data transfer object

data class AnimeDto(
    @Json(name = "mal_id") val malId: Int,
    val title: String,
    @Json(name = "images") val images: ImageWrapper
)

data class ImageWrapper(
    @Json(name = "jpg") val jpg: ImageUrl
)

data class ImageUrl(
    @Json(name = "image_url") val imageUrl: String
)