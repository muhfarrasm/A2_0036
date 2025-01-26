package com.example.project_uas_036.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.repository.PenulisRepository
import com.example.project_uas_036.ui.view.Penulis.DestinasiUpdatePenulis
import kotlinx.coroutines.launch

class UpdatePenulisViewModel (
    savedStateHandle: SavedStateHandle,
    private val Penu: PenulisRepository
): ViewModel(){
    var updatePenulisUiState by mutableStateOf(InsertPenulisUiState())
        private set

    private val _idpenulis: String = checkNotNull(savedStateHandle[DestinasiUpdatePenulis.id_penulis])

    init {
        viewModelScope.launch {
            updatePenulisUiState = Penu.getPenulisbyId(_idpenulis).data
                .toUiStatePenu()
        }
    }

    fun updateInsertPenulisState(insertPenulisUiEvent: InsertPenulisUiEvent){
        updatePenulisUiState = InsertPenulisUiState(insertPenulisUiEvent = insertPenulisUiEvent)
    }

    suspend fun updatePenulis(){
        viewModelScope.launch {
            try {
                Penu.updatePenulis(_idpenulis, updatePenulisUiState.insertPenulisUiEvent.toPenu())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}