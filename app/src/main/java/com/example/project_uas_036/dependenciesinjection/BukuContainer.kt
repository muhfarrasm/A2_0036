package com.example.project_uas_036.dependenciesinjection

import com.example.project_uas_036.repository.BukuRepository
import com.example.project_uas_036.repository.NetworkKontakRepository
import com.example.project_uas_036.service_api.BukuService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer{


    val kontakRepository: BukuRepository

}

class BukuContainer: AppContainer {

    private val baseUrl = "http://10.0.2.2:3500/api/buku/"
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

    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)
    }




    // Implementasikan kontakRepository dengan menggunakan NetworkKontakRepository
    override val kontakRepository: BukuRepository by lazy {
        NetworkKontakRepository(
            bukuApiService = bukuService,

        )
    }
}
