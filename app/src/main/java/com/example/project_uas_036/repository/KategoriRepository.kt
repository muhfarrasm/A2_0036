package com.example.project_uas_036.repository

import android.util.Log
import com.example.project_uas_036.model.AllKategoriResponse
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.KategoriDetailResponse
import com.example.project_uas_036.service_api.KategoriService
import retrofit2.Response
import java.io.IOException

interface KategoriRepository {
    suspend fun insertKategori(kategori: Kategori)
    suspend fun getKategori(): AllKategoriResponse
    suspend fun updateKategori(id_kategori: String, kategori: Kategori)
    suspend fun deleteKategori(id_kategori: String): Response<Void>
    suspend fun getKategoribyId(id_kategori: String): KategoriDetailResponse
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {

    // Mengimplementasikan KategoriRepository
    private val tag = "NetworkKategoriRepository"
    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun getKategori(): AllKategoriResponse =
        kategoriApiService.getAllKategori()

    override suspend fun updateKategori(id_kategori: String, kategori: Kategori) {
        try {
            kategoriApiService.updateKategori(id_kategori, kategori)
            Log.i(tag, "Successfully updated Kategori with ID: $id_kategori")
        } catch (e: Exception) {
            Log.e(tag, "Failed to update Kategori with ID $id_kategori: ${e.message}", e)
        }
    }

    override suspend fun deleteKategori(id_kategori: String): Response<Void> {
        try {
            val response = kategoriApiService.deleteKategori(id_kategori)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: ${response.code()}")
            } else {
                return response
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKategoribyId(id_kategori: String): KategoriDetailResponse {
        return try {
            val response = kategoriApiService.getKategoribyId(id_kategori)
            Log.i(tag, "Successfully fetched kategori by ID $id_kategori: ${response.data}")
            response
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch kategori by ID $id_kategori: ${e.message}", e)
            throw e
        }
    }
}
