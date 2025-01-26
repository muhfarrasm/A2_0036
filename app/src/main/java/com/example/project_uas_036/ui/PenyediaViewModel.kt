package com.example.project_uas_036.ui

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.project_uas_036.PerpusApp
import com.example.project_uas_036.ui.viewmodel.Buku.HomeBukuViewModel

object PenyediaViewModel {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer { HomeBukuViewModel(aplikasiKontak().bukcontainer.kontakRepository) }

    }
}
fun CreationExtras.aplikasiKontak(): PerpusApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpusApp)

