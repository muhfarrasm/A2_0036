package com.example.project_uas_036.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.repository.KategoriRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val kat: KategoriRepository): ViewModel() {
    var KateUiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(insertUiEvent: InsertKategoriUiEvent) {
        KateUiState = InsertKategoriUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertKategori() {
        viewModelScope.launch {
            try {
                kat.insertKategori(KateUiState.insertUiEvent.toKat())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent(),
    val error: String? = null
)

data class InsertKategoriUiEvent(
    val idkategori: String = "",
    val namaKategori: String = "",
    val deskripsiKategori: String = "",
)

fun InsertKategoriUiEvent.toKat(): Kategori = Kategori(
    idkategori = idkategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori,
)

fun Kategori.toUiStateKat(): InsertKategoriUiState = InsertKategoriUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Kategori.toInsertUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    idkategori = idkategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori,
)