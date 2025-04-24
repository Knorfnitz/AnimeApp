package com.example.projekt.navigation

import kotlinx.serialization.Serializable

@Serializable
object AnimeListScreenRoute

@Serializable
object FavoritesScreenRoute

@Serializable
object SearchScreenRoute

@Serializable
data class AnimeDetailScreenRoute(val id: Int)
