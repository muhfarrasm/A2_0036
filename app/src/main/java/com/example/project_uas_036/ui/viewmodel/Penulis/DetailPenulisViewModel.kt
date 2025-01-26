package com.example.project_uas_036.ui.viewmodel.Penulis

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.repository.PenulisRepository
import com.example.project_uas_036.ui.view.Penulis.DestinasiDetailPenulis
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPenulisUiState {
    data class Success(val penu: Penulis) : DetailPenulisUiState()
    object Error : DetailPenulisUiState()
    object Loading : DetailPenulisUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailPenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val tulis: PenulisRepository
) : ViewModel() {

    var penulisDetailState: DetailPenulisUiState by mutableStateOf(DetailPenulisUiState.Loading)
        private set

    private val _idpenu : String = checkNotNull(savedStateHandle[DestinasiDetailPenulis.id_penulis])

    init {
        getpenulisbyId()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getpenulisbyId() {
        viewModelScope.launch {
            penulisDetailState = DetailPenulisUiState.Loading
            penulisDetailState = try {
                val tul = tulis.getPenulisbyId(_idpenu).data
                DetailPenulisUiState.Success(tul)
            } catch (e: IOException) {
                DetailPenulisUiState.Error
            } catch (e: HttpException) {
                DetailPenulisUiState.Error
            }
        }
    }
}