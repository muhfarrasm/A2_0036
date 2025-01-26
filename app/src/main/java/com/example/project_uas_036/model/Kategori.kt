package com.example.project_uas_036.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kategori(

    @SerialName("id_kategori")
    val idkategori: String, // CHAR(10)

    @SerialName("nama_kategori")
    val namaKategori: String, // VARCHAR(255)

    @SerialName("deskripsi_kategori")
    val deskripsiKategori: String // TEXT
)

@Serializable
data class AllKategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kategori>
)

@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kategori
)
