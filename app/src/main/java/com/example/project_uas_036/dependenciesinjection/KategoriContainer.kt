package com.example.project_uas_036.dependenciesinjection

import com.example.project_uas_036.repository.KategoriRepository
import com.example.project_uas_036.repository.NetworkKategoriRepository
import com.example.project_uas_036.service_api.KategoriService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppKategoriContainer{
    val kontakKategoriRepository: KategoriRepository
}

class KategoriContainer: AppKategoriContainer {
    private val baseUrl =  "http://10.0.2.2:3500/api/kategori/"
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

    private val kategoriService: KategoriService by lazy {
        retrofit.create(KategoriService::class.java)
    }

    override val kontakKategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }

}