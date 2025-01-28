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
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.repository.BukuRepository
import com.example.project_uas_036.repository.KategoriRepository
import com.example.project_uas_036.repository.PenerbitRepository
import com.example.project_uas_036.repository.PenulisRepository
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
    private val book: BukuRepository,
    private val kat : KategoriRepository,
    private val terbit : PenerbitRepository,
    private val tulis : PenulisRepository
) : ViewModel() {

    var bukuDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idBuku : String = checkNotNull(savedStateHandle[DestinasiDetailBuku.id_buku])

    init {
        getMahasiswabyNim()
    }
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