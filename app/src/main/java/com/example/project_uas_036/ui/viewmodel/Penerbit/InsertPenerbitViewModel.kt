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

    fun updateInsertPenerbitState(insertUiEvent: InsertPenerbitUiEvent) {
        PeneUiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertUiEvent)
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