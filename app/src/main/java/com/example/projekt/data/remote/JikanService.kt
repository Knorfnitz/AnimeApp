package com.example.projekt.data.remote


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanService {
    @GET("top/anime")
    suspend fun getTopAnime(): AnimeListResponse

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): AnimeDetailResponse
}