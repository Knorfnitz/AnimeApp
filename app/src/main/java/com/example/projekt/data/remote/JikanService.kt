package com.example.projekt.data.remote

import retrofit2.http.GET

interface JikanService {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse
}