package com.example.project_uas_036.ui.viewmodel.Penerbit

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.repository.PenerbitRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenerbitUiState {
    data class Success(val penerbit: List<Penerbit>) : HomePenerbitUiState()
    object Error : HomePenerbitUiState()
    object Loading : HomePenerbitUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomePenerbitViewModel (private val terbit: PenerbitRepository)
    : ViewModel() {
    var PeneUiState: HomePenerbitUiState by mutableStateOf(HomePenerbitUiState.Loading)
        private set

    init {
        getPenerbit()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPenerbit() {
        viewModelScope.launch {
            PeneUiState = HomePenerbitUiState.Loading
            try {
                val response = terbit.getPenerbit()
                if (response.data.isNullOrEmpty()) {
                    PeneUiState = HomePenerbitUiState.Error
                    println("Empty Penerbit data")
                } else {
                    PeneUiState = HomePenerbitUiState.Success(response.data)
                }
            } catch (e: IOException) {
                PeneUiState = HomePenerbitUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                PeneUiState = HomePenerbitUiState.Error
                println("HttpException: ${e.message}")
            }
        }
    }
}


