package com.example.project_uas_036.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.repository.BukuRepository
import com.example.project_uas_036.repository.KategoriRepository
import com.example.project_uas_036.repository.PenerbitRepository
import com.example.project_uas_036.repository.PenulisRepository
import com.example.project_uas_036.ui.view.Buku.DestinasiUpdateBuku
import kotlinx.coroutines.launch

class UpdateBukuViewModel (
    savedStateHandle: SavedStateHandle,
    private val book: BukuRepository,
    private val kat: KategoriRepository,
    private val terbit: PenerbitRepository,
    private val tulis: PenulisRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    //Relasi Kategori, Penerbit ,Penulis
    var katList by mutableStateOf<List<Kategori>>(emptyList())
    var terbitList by mutableStateOf<List<Penerbit>>(emptyList())
    var tulisList by mutableStateOf<List<Penulis>>(emptyList())



    init {
        loadKatTerbitTulis()

    }

    private fun loadKatTerbitTulis() {
        viewModelScope.launch {
            try {
                katList = kat.getKategori().data
                terbitList = terbit.getPenerbit().data
                tulisList = tulis.getPenulis().data
            }catch (e: Exception) {

            }
        }
    }




    private val _idbuku: String = checkNotNull(savedStateHandle[DestinasiUpdateBuku.id_buku])

    init {
        viewModelScope.launch {
            updateUiState = book.getBukubyId(_idbuku).data
                .toUiStateBuku()
        }
    }

    fun updateInsertBukuState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun updateBuku(){
        viewModelScope.launch {
            try {
                book.updateBuku(_idbuku, updateUiState.insertUiEvent.tobook())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}