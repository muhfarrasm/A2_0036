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

    var errorMessages by mutableStateOf<Map<String, String>>(emptyMap())
        private set

    val phoneRegex = Regex("^[0-9]{10,15}$")

    private val _idpenerbit: String = checkNotNull(savedStateHandle[DestinasiUpdatePenerbit.id_penerbit])

    init {
        viewModelScope.launch {
            updatePenerbitUiState = Pene.getPenerbitbyId(_idpenerbit).data
                .toUiStatePene()
        }
    }

    fun validateInput(): Boolean {
        val errors = mutableMapOf<String, String>()

        val phoneRegex = Regex("^[0-9]{10,15}$") // Hanya angka dengan panjang 10-15 digit

        if (updatePenerbitUiState.insertPenerbitUiEvent.namaPenerbit.isBlank()) {
            errors["name"] = "Name cannot be empty"
        }

        if (updatePenerbitUiState.insertPenerbitUiEvent.alamatPenerbit.isBlank()) {
            errors["alamat"] = "Description cannot be empty"
        }

        if (!phoneRegex.matches(updatePenerbitUiState.insertPenerbitUiEvent.teleponPenerbit)) {
            errors["phone"] = "Phone number must be numeric and 10-15 digits long"
        }

        errorMessages = errors
        return errors.isEmpty()
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