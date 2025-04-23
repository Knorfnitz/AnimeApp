package com.example.projekt.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {

    @Query("SELECT * FROM favorite_anime")
    fun getAll(): Flow<List<FavoriteAnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: FavoriteAnimeEntity)

    @Delete
    suspend fun delete(anime: FavoriteAnimeEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_anime WHERE id = :animeId)")
    suspend fun isFavorite(animeId: Int): Boolean
}