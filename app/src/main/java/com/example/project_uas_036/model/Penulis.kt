package com.example.project_uas_036.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penulis(

    val id_penulis: String, // CHAR(10)

    @SerialName("nama_penulis")
    val namaPenulis: String, // VARCHAR(255)

    @SerialName("biografi")
    val biografi: String, // TEXT

    @SerialName("kontak")
    val kontak: String // VARCHAR(255)
)

@Serializable
data class AllPenulisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penulis>
)

@Serializable
data class PenulisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penulis
)
