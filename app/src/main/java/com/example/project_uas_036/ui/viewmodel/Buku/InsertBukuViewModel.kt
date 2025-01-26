package com.example.project_uas_036.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.repository.BukuRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel(private val book: BukuRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

//    // Tambahkan variabel untuk menyimpan daftar kategori
//    var listKategori by mutableStateOf<List<Kategori>>(emptyList())
//        private set

//    init {
//        // Panggil fungsi untuk mendapatkan kategori saat ViewModel diinisialisasi
//        fetchKategori()
//    }

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

//    // Fungsi untuk mengambil data kategori
//    private fun fetchKategori() {
//        viewModelScope.launch {
//            try {
//                // Ambil kategori dan simpan dalam listKategori
//                book.getKategori().collect { kategoriList ->
//                    listKategori = kategoriList
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
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

