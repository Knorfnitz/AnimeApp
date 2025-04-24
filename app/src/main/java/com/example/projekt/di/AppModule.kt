package com.example.projekt.di

import androidx.lifecycle.SavedStateHandle
import com.example.projekt.data.local.FavoritesDatabase
import com.example.projekt.data.local.FavoritesRepository
import com.example.projekt.data.remote.JikanService
import com.example.projekt.data.repository.AnimeRepository
import com.example.projekt.domain.controller.FavoritesController
import com.example.projekt.domain.repository.AnimeRepositoryInterface
import com.example.projekt.domain.repository.FavoritesRepositoryInterface
import com.example.projekt.presentation.anime_detail_screen.AnimeDetailViewModel
import com.example.projekt.presentation.anime_list.AnimeListViewModel
import com.example.projekt.presentation.favorite.FavoritesViewModel
import com.example.projekt.presentation.search.SearchViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    val jikanBaseUrl = "https://api.jikan.moe/v4/"

    single<AnimeRepositoryInterface> {
        AnimeRepository(
            get()
        )
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(jikanBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<JikanService> {
        get<Retrofit>().create(JikanService::class.java)
    }

    single {
        FavoritesDatabase.getDatabase(androidContext()).dao()
    }

    single<FavoritesRepositoryInterface> {
        FavoritesRepository(get())
    }

    single { FavoritesController(get()) }


    viewModel {
        AnimeListViewModel(
            animeRepo = get(),
            favoritesController = get()
        )
    }
    viewModel {
        FavoritesViewModel(
            favoritesController = get()
        )
    }

    viewModel {
        SearchViewModel(
            api = get(),
            favoritesController = get()
        )
    }

    viewModel { (handle: SavedStateHandle) ->
        AnimeDetailViewModel(
            get(),
            handle
        )
    }
}