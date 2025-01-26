package com.example.project_uas_036.dependenciesinjection

import com.example.project_uas_036.repository.NetworkPenulisRepository
import com.example.project_uas_036.repository.PenulisRepository
import com.example.project_uas_036.service_api.PenulisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppPenulisContainer{
    val kontakPenulisRepository: PenulisRepository
}

class PenulisContainer: AppPenulisContainer {
    private val baseUrl = "http://10.0.2.2:3500/api/penulis/"
    private val json = Json { ignoreUnknownKeys = true }
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs request and response body
    }


    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(httpClient)
        .build()

    private val penulisService: PenulisService by lazy {
        retrofit.create(PenulisService::class.java)
    }

    override val kontakPenulisRepository: PenulisRepository by lazy {
        NetworkPenulisRepository(penulisService)
    }

}