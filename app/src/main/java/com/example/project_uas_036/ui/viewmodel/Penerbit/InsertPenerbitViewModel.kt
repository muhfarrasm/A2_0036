package com.example.project_uas_036.ui.viewmodel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.repository.PenerbitRepository
import kotlinx.coroutines.launch

class InsertPenerbitViewModel(private val terbit: PenerbitRepository): ViewModel() {
    var PeneUiState by mutableStateOf(InsertPenerbitUiState())
        private set

    var errorMessages by mutableStateOf(emptyMap<String, String>())
        private set

    fun updateInsertPenerbitState(insertUiEvent: InsertPenerbitUiEvent) {
        PeneUiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertUiEvent)
    }

    private fun validateInput(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(PeneUiState.insertPenerbitUiEvent) {
            if (id_penerbit.isBlank()) {
                errors["id_penerbit"] = "ID Penerbit tidak boleh kosong"
            }
            if (namaPenerbit.isBlank()) {
                errors["namaPenerbit"] = "Nama Penerbit tidak boleh kosong"
            }
            if (alamatPenerbit.isBlank()) {
                errors["alamatPenerbit"] = "Alamat Penerbit tidak boleh kosong"
            }
            if (teleponPenerbit.isBlank()) {
                errors["teleponPenerbit"] = "Telepon Penerbit tidak boleh kosong"
            } else if (!teleponPenerbit.matches("^08\\d{8,12}\$".toRegex())) {
                errors["teleponPenerbit"] = "Nomor telepon harus dimulai dengan '08' dan terdiri dari 10-13 digit"
            }
        }

        errorMessages = errors
        return errors.isEmpty()
    }

    suspend fun insertPenerbit() {
        viewModelScope.launch {
            try {
                terbit.insertPenerbit(PeneUiState.insertPenerbitUiEvent.toPene())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenerbitUiState(
    val insertPenerbitUiEvent: InsertPenerbitUiEvent = InsertPenerbitUiEvent()
)

data class InsertPenerbitUiEvent(
    val id_penerbit: String = "",
    val namaPenerbit: String = "",
    val alamatPenerbit: String = "",
    val teleponPenerbit: String = "",

    )

fun InsertPenerbitUiEvent.toPene(): Penerbit = Penerbit(
    id_penerbit = id_penerbit,
    namaPenerbit = namaPenerbit,
    alamatPenerbit = alamatPenerbit,
    teleponPenerbit = teleponPenerbit
)

fun Penerbit.toUiStatePene(): InsertPenerbitUiState = InsertPenerbitUiState(
    insertPenerbitUiEvent = toInsertUiEvent()
)

fun Penerbit.toInsertUiEvent(): InsertPenerbitUiEvent = InsertPenerbitUiEvent(
    id_penerbit = id_penerbit,
    namaPenerbit = namaPenerbit,
    alamatPenerbit = alamatPenerbit,
    teleponPenerbit = teleponPenerbit
)