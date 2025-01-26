package com.example.project_uas_036.service_api


import com.example.project_uas_036.model.AllPenulisResponse
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.model.PenulisDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenulisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )


    @POST("store")
    suspend fun insertPenulis (@Body penulis: Penulis)


    @GET(".")
    suspend fun getAllPenulis(): AllPenulisResponse
    //List<Mahasiswa>



    @GET("{id_penulis}")
    suspend fun getPenulisbyId(@Path("id_penulis") id_penulis: String): PenulisDetailResponse


    @PUT("{id_penulis}")
    suspend fun updatePenulis(@Path("id_penulis") id_penulis: String, @Body penulis: Penulis)


    @DELETE("{id_penulis}")
    suspend fun deletePenulis(@Path("id_penulis")id_penulis: String): Response<Void>
}