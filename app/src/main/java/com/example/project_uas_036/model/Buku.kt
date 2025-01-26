package com.example.project_uas_036.model

import kotlinx.serialization.Serializable

@Serializable
data class Buku(

    val id_buku: String, // CHAR(10)


    val nama_buku: String, // VARCHAR(255)


    val deskripsi_buku: String, // TEXT


    val tanggal_terbit: String, // VARCHAR(60)


    val status_buku: String, // VARCHAR(50)


    val id_kategori: String, // CHAR(10) (Nullable karena foreign key dapat NULL)


    val id_penulis: String, // CHAR(10) (Nullable karena foreign key dapat NULL)


    val id_penerbit: String // CHAR(10) (Nullable karena foreign key dapat NULL)
)

@Serializable
data class AllBukuResponse(
    val status: Boolean,
    val message: String,
    val data: List<Buku>
)

@Serializable
data class BukuDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Buku
)


