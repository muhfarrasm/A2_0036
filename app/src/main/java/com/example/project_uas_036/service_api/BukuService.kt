package com.example.project_uas_036.service_api

import com.example.project_uas_036.model.AllBukuResponse
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.model.BukuDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BukuService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )


    @POST("store")
    suspend fun insertBuku (@Body buku : Buku)


    @GET(".")
    suspend fun getAllBuku(): AllBukuResponse
    //List<Mahasiswa>



    @GET("{id_buku}")
    suspend fun getBukubyId(@Path("id_buku") id_buku: String): BukuDetailResponse



    @PUT("{id_buku}")
    suspend fun updateBuku(@Path("id_buku")id_buku: String, @Body buku: Buku)


    @DELETE("{id_buku}")
    suspend fun deleteBuku(@Path("id_buku")id_buku: String): Response<Void>




//    @GET("api/kategori")
//    fun getKategori(): Flow<List<Kategori>>

}