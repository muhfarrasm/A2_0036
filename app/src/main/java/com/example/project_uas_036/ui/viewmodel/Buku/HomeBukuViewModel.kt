package com.example.project_uas_036.ui.viewmodel.Buku

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.repository.BukuRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val buku: List<Buku>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeBukuViewModel (private val book: BukuRepository)
    : ViewModel(){
    var BookUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getBuku()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getBuku() {
        viewModelScope.launch {
            BookUiState = HomeUiState.Loading
            BookUiState = try {
                HomeUiState.Success(book.getBuku().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteBuku(id_buku: String) {
        viewModelScope.launch {
            try {
                book.deleteBuku(id_buku)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}