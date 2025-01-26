package com.example.project_uas_036.service_api

import com.example.project_uas_036.model.AllPenerbitResponse
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.PenerbitDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenerbitService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )


    @POST("store")
    suspend fun insertPenerbit (@Body penerbit: Penerbit)


    @GET(".")
    suspend fun getAllPenerbit(): AllPenerbitResponse
    //List<Mahasiswa>



    @GET("{id_penerbit}")
    suspend fun getPenerbitbyId(@Path("id_penerbit") id_penerbit: String): PenerbitDetailResponse


    @PUT("{id_penerbit}")
    suspend fun updatePenerbit(@Path("id_penerbit") id_penerbit: String, @Body penerbit: Penerbit)


    @DELETE("{id_penerbit}")
    suspend fun deletePenerbit(@Path("id_penerbit")id_penerbit: String): Response<Void>
}