package com.example.project_uas_036.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.repository.KategoriRepository
import com.example.project_uas_036.ui.view.Kategori.DestinasiUpdateKategori
import kotlinx.coroutines.launch

class UpdateKategoriViewModel (
    savedStateHandle: SavedStateHandle,
    private val Kat: KategoriRepository
): ViewModel(){
    var updateKategoriUiState by mutableStateOf(InsertKategoriUiState())
        private set

    private val _idkategori: String = checkNotNull(savedStateHandle[DestinasiUpdateKategori.id_kategori])

    init {
        viewModelScope.launch {
            updateKategoriUiState = Kat.getKategoribyId(_idkategori).data
                .toUiStateKat()
        }
    }

    fun updateInsertKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent){
        updateKategoriUiState = InsertKategoriUiState(insertUiEvent = insertKategoriUiEvent)
    }

    suspend fun updateKategori(){
        viewModelScope.launch {
            try {
                Kat.updateKategori(_idkategori, updateKategoriUiState.insertUiEvent.toKat())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}




