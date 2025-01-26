package com.example.project_uas_036.ui.viewmodel.Kategori

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.repository.KategoriRepository
import com.example.project_uas_036.ui.view.Kategori.DestinasiDetailKategori
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailKategoriUiState {
    data class Success(val kategori: Kategori) : DetailKategoriUiState()
    object Error : DetailKategoriUiState()
    object Loading : DetailKategoriUiState()
}
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kat: KategoriRepository
) : ViewModel() {

    var kategoriDetailState: DetailKategoriUiState by mutableStateOf(DetailKategoriUiState.Loading)
        private set

    private val idKategori : String = checkNotNull(savedStateHandle[DestinasiDetailKategori.idkategori])

    init {
        getKategoribyid()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getKategoribyid() {
        viewModelScope.launch {
            kategoriDetailState = DetailKategoriUiState.Loading
            Log.d("DetailKategoriViewModel", "Loading data for ID: $idKategori")
            kategoriDetailState = try {
                val cat = kat.getKategoribyId(idKategori)
                Log.d("DetailKategoriViewModel", "Data fetched successfully: $cat")
                DetailKategoriUiState.Success(cat.data)
            } catch (e: IOException) {
                Log.e("DetailKategoriViewModel", "IOException: ${e.message}")
                DetailKategoriUiState.Error
            } catch (e: HttpException) {
                Log.e("DetailKategoriViewModel", "HttpException: ${e.message}")
                DetailKategoriUiState.Error
            }
        }
    }

}