package com.example.project_uas_036.ui.viewmodel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.repository.PenerbitRepository
import com.example.project_uas_036.ui.view.Penerbit.DestinasiUpdatePenerbit
import kotlinx.coroutines.launch

class UpdatePenebitViewModel (
    savedStateHandle: SavedStateHandle,
    private val Pene: PenerbitRepository
): ViewModel(){
    var updatePenerbitUiState by mutableStateOf(InsertPenerbitUiState())
        private set

    private val _idpenerbit: String = checkNotNull(savedStateHandle[DestinasiUpdatePenerbit.id_penerbit])

    init {
        viewModelScope.launch {
            updatePenerbitUiState = Pene.getPenerbitbyId(_idpenerbit).data
                .toUiStatePene()
        }
    }

    fun updateInsertPenerbitState(insertPenerbitUiEvent: InsertPenerbitUiEvent){
        updatePenerbitUiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertPenerbitUiEvent)
    }

    suspend fun updatePenerbit(){
        viewModelScope.launch {
            try {
                Pene.updatePenerbit(_idpenerbit, updatePenerbitUiState.insertPenerbitUiEvent.toPene())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun deletePenerbit(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                Pene.deletePenerbit(_idpenerbit) // Pastikan fungsi ini ada di repository
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


}