package com.example.project_uas_036.repository

import android.util.Log
import com.example.project_uas_036.model.AllBukuResponse
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.model.BukuDetailResponse
import com.example.project_uas_036.service_api.BukuService
import java.io.IOException

interface BukuRepository{
    suspend fun insertBuku(buku: Buku)

    suspend fun getBuku(): AllBukuResponse

    suspend fun updateBuku(id_buku: String, buku: Buku)

    suspend fun deleteBuku(id_buku: String)

    suspend fun getBukubyId(id_buku: String): BukuDetailResponse

//    suspend fun getKategoribyId(id_kategori: String): KategoriDetailResponse

   // suspend fun getPenulisbyId(id_penulis: String): PenulisDetailResponse
//    suspend fun getPenerbit(): AllPenerbitResponse
//
//    fun getKategori(): Flow<List<Kategori>>
//    suspend fun getPenulis(): AllPenulisResponse
}

class NetworkKontakRepository(
    private val bukuApiService: BukuService,
//    private val penerbitApiService: PenerbitService,
    //private val penulisApiService: PenulisService,
//    private val kategoriApiService: KategoriService

): BukuRepository {
    private val tag = "NetworkKontakRepository"

    override suspend fun insertBuku(buku: Buku) {
        try {
            bukuApiService.insertBuku(buku)
            Log.i(tag, "Successfully inserted buku: $buku")
        } catch (e: Exception) {
            Log.e(tag, "Failed to insert buku: ${e.message}", e)
        }
    }

    override suspend fun getBuku(): AllBukuResponse {
        return try {
            val response = bukuApiService.getAllBuku()
            Log.i(tag, "Successfully fetched all buku: ${response.data}")
            response
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch all buku: ${e.message}", e)
            throw e
        }
    }

    override suspend fun updateBuku(id_buku: String, buku: Buku) {
        try {
            bukuApiService.updateBuku(id_buku, buku)
            Log.i(tag, "Successfully updated buku with ID: $id_buku")
        } catch (e: Exception) {
            Log.e(tag, "Failed to update buku with ID $id_buku: ${e.message}", e)
        }
    }

    override suspend fun deleteBuku(id_buku: String) {
        try {
            val response = bukuApiService.deleteBuku(id_buku)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete buku. HTTP Status code: ${response.code()}")
            }
            Log.i(tag, "Successfully deleted buku with ID: $id_buku")
        } catch (e: Exception) {
            Log.e(tag, "Failed to delete buku with ID $id_buku: ${e.message}", e)
        }
    }

    override suspend fun getBukubyId(id_buku: String): BukuDetailResponse {
        return try {
            val response = bukuApiService.getBukubyId(id_buku)
            Log.i(tag, "Successfully fetched buku by ID $id_buku: ${response.data}")
            response
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch buku by ID $id_buku: ${e.message}", e)
            throw e
        }
    }

//    override suspend fun getKategoribyId(id_kategori: String): KategoriDetailResponse {
//        return try {
//            val response = kategoriApiService.getKategoribyId(id_kategori)
//            Log.i(tag, "Successfully fetched kategori by ID $id_kategori: ${response.data}")
//            response
//        } catch (e: Exception) {
//            Log.e(tag, "Failed to fetch kategori by ID $id_kategori: ${e.message}", e)
//            throw e
//        }
//    }

//    override suspend fun getPenulisbyId(id_penulis: String): PenulisDetailResponse {
//        return try {
//            val response = penulisApiService.getPenulisbyId(id_penulis)
//            Log.i(tag, "Successfully fetched Penulis by ID $id_penulis: ${response.data}")
//            response
//        } catch (e: Exception) {
//            Log.e(tag, "Failed to fetch Penulis by ID $id_penulis: ${e.message}", e)
//            throw e
//        }
//    }




//    override suspend fun getPenerbit(): AllPenerbitResponse =
//        penerbitApiService.getAllPenerbit()
//
//    override suspend fun getPenulis(): AllPenulisResponse =
//        penulisApiService.getAllPenulis()
//
//    override fun getKategori(): Flow<List<Kategori>> = flow {
//        try {
//            val response = kategoriApiService.getAllKategori()
//            emit(response.data) // Emit data dari AllKategoriResponse
//        } catch (e: Exception) {
//            Log.e(tag, "Failed to fetch kategori: ${e.message}", e)
//            throw e
//        }
//    }


}