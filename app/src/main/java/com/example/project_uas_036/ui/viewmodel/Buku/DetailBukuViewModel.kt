package com.example.project_uas_036.ui.viewmodel.Buku

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.repository.BukuRepository
import com.example.project_uas_036.ui.view.Buku.DestinasiDetailBuku
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    data class Success(val buku: Buku) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val book: BukuRepository
) : ViewModel() {

    var bukuDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idBuku : String = checkNotNull(savedStateHandle[DestinasiDetailBuku.id_buku])

    init {
        getMahasiswabyNim()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMahasiswabyNim() {
        viewModelScope.launch {
            bukuDetailState = DetailUiState.Loading
            bukuDetailState = try {
                val buk = book.getBukubyId(_idBuku).data
                DetailUiState.Success(buk)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}