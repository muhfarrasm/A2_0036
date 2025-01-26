package com.example.project_uas_036.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.repository.PenulisRepository
import kotlinx.coroutines.launch

class InsertPenulisViewModel(private val tulis: PenulisRepository): ViewModel() {
    var PenuUiState by mutableStateOf(InsertPenulisUiState())
        private set

    fun updateInsertPenulisState(insertUiEvent: InsertPenulisUiEvent) {
        PenuUiState = InsertPenulisUiState(insertPenulisUiEvent = insertUiEvent)
    }

    suspend fun insertPenulis() {
        viewModelScope.launch {
            try {
                tulis.insertPenulis(PenuUiState.insertPenulisUiEvent.toPenu())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenulisUiState(
    val insertPenulisUiEvent: InsertPenulisUiEvent = InsertPenulisUiEvent()
)

data class InsertPenulisUiEvent(
    val id_penulis: String = "",
    val namaPenulis: String = "",
    val biografi: String = "",
    val kontak: String = "",

    )

fun InsertPenulisUiEvent.toPenu(): Penulis = Penulis(
    id_penulis = id_penulis,
    namaPenulis = namaPenulis,
    biografi = biografi,
    kontak = kontak
)

fun Penulis.toUiStatePenu(): InsertPenulisUiState = InsertPenulisUiState(
    insertPenulisUiEvent = toInsertUiEvent()
)

fun Penulis.toInsertUiEvent(): InsertPenulisUiEvent = InsertPenulisUiEvent(
    id_penulis = id_penulis,
    namaPenulis = namaPenulis,
    biografi = biografi,
    kontak = kontak
)