package com.example.project_uas_036.repository

import android.util.Log
import com.example.project_uas_036.model.AllPenulisResponse
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.model.PenulisDetailResponse
import com.example.project_uas_036.service_api.PenulisService
import java.io.IOException

interface PenulisRepository{


    suspend fun insertPenulis(penulis: Penulis)

    suspend fun getPenulis(): AllPenulisResponse

    suspend fun updatePenulis(idPenulis: String, penulis: Penulis)

    suspend fun deletePenulis(idPenulis: String)

    suspend fun getPenulisbyId(idPenulis: String): PenulisDetailResponse
}

class NetworkPenulisRepository(
    private val penulisApiService: PenulisService
): PenulisRepository {
    private val tag = "NetworkPenulisRepository"
    override suspend fun insertPenulis(penulis: Penulis) {
        penulisApiService.insertPenulis(penulis)
    }

    override suspend fun getPenulis(): AllPenulisResponse =
        penulisApiService.getAllPenulis()

    override suspend fun updatePenulis(id_penulis: String, penulis: Penulis) {
        penulisApiService.updatePenulis(id_penulis, penulis)
    }

    override suspend fun deletePenulis(id_penulis: String) {
        try {
            val response = penulisApiService.deletePenulis(id_penulis)
            if (!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP Status code:" + "${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPenulisbyId(id_penulis: String): PenulisDetailResponse {
        return try {
            val response = penulisApiService.getPenulisbyId(id_penulis)
            Log.i(tag, "Successfully fetched penulis by ID $id_penulis: ${response.data}")
            response
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch penulis by ID $id_penulis: ${e.message}", e)
            throw e
        }
    }
}