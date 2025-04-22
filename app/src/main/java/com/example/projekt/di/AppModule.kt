package com.example.projekt.di

import com.example.projekt.data.remote.JikanService
import com.example.projekt.data.repository.AnimeRepository
import com.example.projekt.domain.repository.AnimeRepositoryInterface
import com.example.projekt.presentation.anime_list.AnimeListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    viewModel {
        AnimeListViewModel(get())
    }
}