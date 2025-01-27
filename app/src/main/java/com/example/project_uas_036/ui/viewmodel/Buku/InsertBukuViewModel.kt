package com.example.project_uas_036.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.repository.BukuRepository
import com.example.project_uas_036.repository.KategoriRepository
import com.example.project_uas_036.repository.PenerbitRepository
import com.example.project_uas_036.repository.PenulisRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel(
    private val book: BukuRepository,
    private val kat: KategoriRepository,
    private val terbit: PenerbitRepository,
    private val penulis: PenulisRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    var kategoriList by mutableStateOf<List<Kategori>>(emptyList())
    var penulisList by mutableStateOf<List<Penulis>>(emptyList())
    var penerbitList by mutableStateOf<List<Penerbit>>(emptyList())


    init {
        // Panggil fungsi untuk mendapatkan kategori,penulis,penerbit saat ViewModel diinisialisasi
        loadkategoripenulispenerbit()
    }

    private fun loadkategoripenulispenerbit() {
        viewModelScope.launch {
            try {
                kategoriList = kat.getKategori().data
                penulisList = penulis.getPenulis().data
                penerbitList = terbit.getPenerbit().data
            } catch (e: Exception) {

            }
        }
    }

    fun updateInsertBukuState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertBuku() {
        viewModelScope.launch {
            try {
                // Insert buku menggunakan data dari UI state
                book.insertBuku(uiState.insertUiEvent.tobook())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun isValidState(): Boolean {
        val event = uiState.insertUiEvent
        return event.nama_buku.isNotBlank() &&
                event.deskripsi_buku.isNotBlank() &&
                event.tanggal_terbit.isNotBlank() &&
                event.status_buku.isNotBlank() &&
                event.id_kategori.isNotBlank() &&
                event.id_penulis.isNotBlank() &&
                event.id_penerbit.isNotBlank()
    }

}

// Data class untuk UI state
data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

// Data class untuk menyimpan data input buku
data class InsertUiEvent(
    val id_buku: String = "",
    val nama_buku: String = "",
    val deskripsi_buku: String = "",
    val tanggal_terbit: String = "",
    val status_buku: String = "",
    val id_kategori: String = "",
    val id_penulis: String = "",
    val id_penerbit: String = ""
)

// Fungsi untuk mengkonversi InsertUiEvent ke Buku
fun InsertUiEvent.tobook(): Buku = Buku(
    id_buku = id_buku,
    nama_buku = nama_buku,
    tanggal_terbit = tanggal_terbit,
    status_buku = status_buku,
    deskripsi_buku = deskripsi_buku,
    id_kategori = id_kategori,
    id_penulis = id_penulis,
    id_penerbit = id_penerbit
)

// Fungsi untuk mengkonversi Buku ke InsertUiState
fun Buku.toUiStateBuku(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

// Fungsi untuk mengkonversi Buku ke InsertUiEvent
fun Buku.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_buku = id_buku,
    nama_buku = nama_buku,
    tanggal_terbit = tanggal_terbit,
    status_buku = status_buku,
    deskripsi_buku = deskripsi_buku,
    id_kategori = id_kategori,
    id_penulis = id_penulis,
    id_penerbit = id_penerbit
)

