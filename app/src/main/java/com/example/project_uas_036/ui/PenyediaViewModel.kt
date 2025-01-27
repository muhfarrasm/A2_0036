package com.example.project_uas_036.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.project_uas_036.PerpusApp

import com.example.project_uas_036.ui.viewmodel.Buku.DetailBukuViewModel
import com.example.project_uas_036.ui.viewmodel.Buku.HomeBukuViewModel
import com.example.project_uas_036.ui.viewmodel.Buku.InsertBukuViewModel
import com.example.project_uas_036.ui.viewmodel.Buku.UpdateBukuViewModel
import com.example.project_uas_036.ui.viewmodel.Kategori.DetailKategoriViewModel
import com.example.project_uas_036.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.project_uas_036.ui.viewmodel.Kategori.InsertKategoriViewModel
import com.example.project_uas_036.ui.viewmodel.Kategori.UpdateKategoriViewModel
import com.example.project_uas_036.ui.viewmodel.Penerbit.HomePenerbitViewModel
import com.example.project_uas_036.ui.viewmodel.Penerbit.InsertPenerbitViewModel
import com.example.project_uas_036.ui.viewmodel.Penerbit.UpdatePenebitViewModel
import com.example.project_uas_036.ui.viewmodel.Penulis.DetailPenulisViewModel
import com.example.project_uas_036.ui.viewmodel.Penulis.HomePenulisViewModel
import com.example.project_uas_036.ui.viewmodel.Penulis.InsertPenulisViewModel
import com.example.project_uas_036.ui.viewmodel.Penulis.UpdatePenulisViewModel

object PenyediaViewModel {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer { HomeBukuViewModel(aplikasiKontak().bukcontainer.kontakRepository) }
        initializer {
            InsertBukuViewModel(
                book = aplikasiKontak().bukcontainer.kontakRepository,
                kat = aplikasiKontak().katcontainer.kontakKategoriRepository,
                terbit = aplikasiKontak().penerbitcontainer.kontakPenerbitRepository,
                penulis = aplikasiKontak().penuliscontainer.kontakPenulisRepository
            )
        }
        initializer { DetailBukuViewModel(createSavedStateHandle(),aplikasiKontak().bukcontainer.kontakRepository) }
        initializer { UpdateBukuViewModel(createSavedStateHandle(),aplikasiKontak().bukcontainer.kontakRepository) }

        initializer { HomeKategoriViewModel(aplikasiKontak().katcontainer.kontakKategoriRepository) }
        initializer { InsertKategoriViewModel(aplikasiKontak().katcontainer.kontakKategoriRepository) }
        initializer { DetailKategoriViewModel(createSavedStateHandle(),aplikasiKontak().katcontainer.kontakKategoriRepository) }
        initializer {
            Log.d("PenyediaViewModel", "Kategori Repository: ${aplikasiKontak().katcontainer.kontakKategoriRepository}")
            UpdateKategoriViewModel(createSavedStateHandle(),aplikasiKontak().katcontainer.kontakKategoriRepository) }

        initializer { HomePenulisViewModel(aplikasiKontak().penuliscontainer.kontakPenulisRepository) }
        initializer { InsertPenulisViewModel(aplikasiKontak().penuliscontainer.kontakPenulisRepository) }
        initializer { UpdatePenulisViewModel(createSavedStateHandle(),aplikasiKontak().penuliscontainer.kontakPenulisRepository) }
        initializer { DetailPenulisViewModel(createSavedStateHandle(),aplikasiKontak().penuliscontainer.kontakPenulisRepository) }

        initializer { HomePenerbitViewModel(aplikasiKontak().penerbitcontainer.kontakPenerbitRepository) }
        initializer { InsertPenerbitViewModel(aplikasiKontak().penerbitcontainer.kontakPenerbitRepository) }
        initializer { UpdatePenebitViewModel(createSavedStateHandle(),aplikasiKontak().penerbitcontainer.kontakPenerbitRepository) }

    }
}
fun CreationExtras.aplikasiKontak(): PerpusApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpusApp)

