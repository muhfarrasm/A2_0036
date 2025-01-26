package com.example.project_uas_036.service_api

import com.example.project_uas_036.model.AllKategoriResponse
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.KategoriDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KategoriService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )


    @POST("store")
    suspend fun insertKategori (@Body kategori: Kategori)


    @GET(".")
    suspend fun getAllKategori(): AllKategoriResponse
    //List<Mahasiswa>



    @GET("{id_kategori}")
    suspend fun getKategoribyId(@Path("id_kategori") id_kategori: String): KategoriDetailResponse


    @PUT("{id_kategori}")
    suspend fun updateKategori(@Path("id_kategori") id_kategori: String, @Body kategori: Kategori)


    @DELETE("{id_kategori}")
    suspend fun deleteKategori(@Path("id_kategori")id_kategori: String): Response<Void>
}