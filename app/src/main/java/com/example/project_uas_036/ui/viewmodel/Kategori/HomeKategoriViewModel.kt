package com.example.project_uas_036.ui.viewmodel.Kategori

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.repository.KategoriRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeKategoriUiState {
    data class Success(val kategori: List<Kategori>) : HomeKategoriUiState()
    object Error : HomeKategoriUiState()
    object Loading : HomeKategoriUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeKategoriViewModel (private val kat: KategoriRepository)
    : ViewModel(){
    var KateUiState: HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set

    init {
        getKategori()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getKategori() {
        viewModelScope.launch {
            KateUiState = HomeKategoriUiState.Loading
            try {
                val response = kat.getKategori()
                if (response.data.isNullOrEmpty()) {
                    KateUiState = HomeKategoriUiState.Error
                    println("Empty kategori data")
                } else {
                    KateUiState = HomeKategoriUiState.Success(response.data)
                }
            } catch (e: IOException) {
                KateUiState = HomeKategoriUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                KateUiState = HomeKategoriUiState.Error
                println("HttpException: ${e.message}")
            }
        }
    }
    fun onDetailKategoriClick(kategoriId: String) {
        // Logika yang ingin kamu lakukan dengan kategoriId
        println("Detail kategori dengan ID: $kategoriId")
        // Di sini bisa memanggil navigasi atau request untuk mengambil detail kategori
    }

    fun deleteKategori(idkategori: String) {
        viewModelScope.launch {
            try {
                println("Deleting kategori with ID: $idkategori")
                kat.deleteKategori(idkategori)
            } catch (e: IOException) {
                KateUiState = HomeKategoriUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                KateUiState = HomeKategoriUiState.Error
                println("HttpException: ${e.message}")
            }
        }
    }
}