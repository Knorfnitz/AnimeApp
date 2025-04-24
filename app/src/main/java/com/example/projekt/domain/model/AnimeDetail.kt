package com.example.projekt.domain.model

data class AnimeDetail (
        val id: Int,
        val title: String,
        val titleEnglish: String?,
        val titleJapanese: String?,
        val imageUrl: String,
        val score: Double?,
        val rank: Int?,
        val episodes: Int?,
        val status: String?,
        val type: String?,
        val year: Int?,
        val rating: String?,
        val trailerUrl: String?,
        val trailerThumbnail: String?
)
