package com.example.project_uas_036.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penerbit(

    val id_penerbit: String, // CHAR(10)

    @SerialName("nama_penerbit")
    val namaPenerbit: String, // VARCHAR(255)

    @SerialName("alamat_penerbit")
    val alamatPenerbit: String, // TEXT

    @SerialName("telepon_penerbit")
    val teleponPenerbit: String // VARCHAR(20)
)

@Serializable
data class AllPenerbitResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penerbit>
)

@Serializable
data class PenerbitDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penerbit
)
