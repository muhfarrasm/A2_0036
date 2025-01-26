package com.example.project_uas_036.ui.viewmodel.Penulis

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.repository.PenulisRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenulisUiState {
    data class Success(val penulis: List<Penulis>) : HomePenulisUiState()
    object Error : HomePenulisUiState()
    object Loading : HomePenulisUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomePenulisViewModel (private val tulis: PenulisRepository)
    : ViewModel(){
    var PenuUiState: HomePenulisUiState by mutableStateOf(HomePenulisUiState.Loading)
        private set

    init {
        getPenulis()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPenulis() {
        viewModelScope.launch {
            PenuUiState = HomePenulisUiState.Loading
            try {
                val response = tulis.getPenulis()
                if (response.data.isNullOrEmpty()) {
                    PenuUiState = HomePenulisUiState.Error
                    println("Empty Penulis data")
                } else {
                    PenuUiState = HomePenulisUiState.Success(response.data)
                }
            } catch (e: IOException) {
                PenuUiState = HomePenulisUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                PenuUiState = HomePenulisUiState.Error
                println("HttpException: ${e.message}")
            }
        }
    }


    fun deletePenulis(idpenulis: String) {
        viewModelScope.launch {
            try {
                println("Deleting kategori with ID: $idpenulis")
                tulis.deletePenulis(idpenulis)
            } catch (e: IOException) {
                PenuUiState = HomePenulisUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                PenuUiState = HomePenulisUiState.Error
                println("HttpException: ${e.message}")
            }
        }
    }

    fun updatePenulis(idPenulis: String, updatedPenulis: Penulis) {
        viewModelScope.launch {
            try {
                println("Updating penulis with ID: $idPenulis")
                tulis.updatePenulis(idPenulis, updatedPenulis)
                getPenulis() // Refresh data setelah update
            } catch (e: IOException) {
                PenuUiState = HomePenulisUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                PenuUiState = HomePenulisUiState.Error
                println("HttpException: ${e.message}")
            }
        }
    }

}