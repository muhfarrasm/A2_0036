package com.example.project_uas_036.ui.view.Penerbit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Penerbit.InsertPenerbitUiEvent
import com.example.project_uas_036.ui.viewmodel.Penerbit.InsertPenerbitUiState
import com.example.project_uas_036.ui.viewmodel.Penerbit.InsertPenerbitViewModel
import kotlinx.coroutines.launch

object DestinasiTambahPenerbit : DestinasiNavigasi {
    override val route = "Tambah Penerbit"
    override val titleRes = "Masukkan Penerbit"
}
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPenerbitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTambahPenerbit.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,

                )
        }
    ){innerPadding ->
        EntryBody(
            insertPenerbitUiState = viewModel.PeneUiState,
            errorMessages = viewModel.errorMessages,
            onpeneValueChange = viewModel::updateInsertPenerbitState,
            onSaveClick = {
                coroutineScope.launch {
                    try {
                        viewModel.insertPenerbit()
                        navigateBack()
                    } catch (e: Exception) {
                        Log.e("EntryPenerbit", "Error saat menyimpan penerbit: ${e.message}")
                    }
                }

            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertPenerbitUiState: InsertPenerbitUiState,
    onpeneValueChange: (InsertPenerbitUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    errorMessages: Map<String, String>,
    modifier: Modifier = Modifier
) {
    // Scroll state hanya dideklarasikan di dalam EntryBody
    //val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
        //.verticalScroll(scrollState)
        //.verticalScroll(scrollState) // Vertical scroll hanya ada di sini
    ) {
        FormInput(
            insertPenerbitUiEvent = insertPenerbitUiState.insertPenerbitUiEvent,
            onValueChange = onpeneValueChange,
            errorMessages = errorMessages,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (insertPenerbitUiState.insertPenerbitUiEvent.id_penerbit.isEmpty() ||
                    insertPenerbitUiState.insertPenerbitUiEvent.namaPenerbit.isEmpty() ||
                    insertPenerbitUiState.insertPenerbitUiEvent.alamatPenerbit.isEmpty() ||
                    insertPenerbitUiState.insertPenerbitUiEvent.teleponPenerbit.isEmpty()
                ) {
                    println("Harap isi semua data penerbit dengan lengkap!")
                } else {
                    onSaveClick()
                }
            },

            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInput(
    insertPenerbitUiEvent: InsertPenerbitUiEvent,
    onValueChange: (InsertPenerbitUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorMessages: Map<String, String> = emptyMap()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = insertPenerbitUiEvent.id_penerbit,
            onValueChange = { onValueChange(insertPenerbitUiEvent.copy(id_penerbit = it)) },
            label = { Text("Id Penerbit") },
            isError = errorMessages.containsKey("id_penerbit"),
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        if (errorMessages.containsKey("id_penerbit")) {
            Text(
                text = errorMessages["id_penerbit"] ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = insertPenerbitUiEvent.namaPenerbit,
            onValueChange = { onValueChange(insertPenerbitUiEvent.copy(namaPenerbit = it)) },
            label = { Text("Nama Penerbit") },
            isError = errorMessages.containsKey("namaPenerbit"),
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        if (errorMessages.containsKey("namaPenerbit")) {
            Text(
                text = errorMessages["namaPenerbit"] ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = insertPenerbitUiEvent.alamatPenerbit,
            onValueChange = { onValueChange(insertPenerbitUiEvent.copy(alamatPenerbit = it)) },
            label = { Text("Alamat") },
            isError = errorMessages.containsKey("alamatPenerbit"),
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        if (errorMessages.containsKey("alamatPenerbit")) {
            Text(
                text = errorMessages["alamatPenerbit"] ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = insertPenerbitUiEvent.teleponPenerbit,
            onValueChange = { onValueChange(insertPenerbitUiEvent.copy(teleponPenerbit = it)) },
            label = { Text("Telepon Penerbit") },
            isError = errorMessages.containsKey("teleponPenerbit"),
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        if (errorMessages.containsKey("teleponPenerbit")) {
            Text(
                text = errorMessages["teleponPenerbit"] ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}
