package com.example.project_uas_036.repository

import com.example.project_uas_036.model.AllPenerbitResponse
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.PenerbitDetailResponse
import com.example.project_uas_036.service_api.PenerbitService
import java.io.IOException

interface PenerbitRepository{
    suspend fun insertPenerbit(penerbit: Penerbit)

    suspend fun getPenerbit(): AllPenerbitResponse

    suspend fun updatePenerbit(idPenerbit: String, penerbit: Penerbit)

    suspend fun deletePenerbit(idPenerbit: String)

    suspend fun getPenerbitbyId(idPenerbit: String): PenerbitDetailResponse
}

class NetworkPenerbitRepository(
    private val penerbitApiService: PenerbitService
): PenerbitRepository {
    override suspend fun insertPenerbit(penerbit: Penerbit) {
        penerbitApiService.insertPenerbit(penerbit)
    }

    override suspend fun getPenerbit(): AllPenerbitResponse =
        penerbitApiService.getAllPenerbit()

    override suspend fun updatePenerbit(id_penerbit: String, penerbit: Penerbit) {
        penerbitApiService.updatePenerbit(id_penerbit, penerbit)
    }

    override suspend fun deletePenerbit(id_penerbit: String) {
        try {
            val response = penerbitApiService.deletePenerbit(id_penerbit)
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

    override suspend fun getPenerbitbyId(id_penerbit: String): PenerbitDetailResponse {
        return penerbitApiService.getPenerbitbyId(id_penerbit)
    }
}