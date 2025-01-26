package com.example.project_uas_036.ui.view.Penulis

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Penulis.InsertPenulisUiEvent
import com.example.project_uas_036.ui.viewmodel.Penulis.InsertPenulisUiState
import com.example.project_uas_036.ui.viewmodel.Penulis.InsertPenulisViewModel
import kotlinx.coroutines.launch

object DestinasiTambahPenulis : DestinasiNavigasi {
    override val route = "Tambah Penulis"
    override val titleRes = "Masukkan Penulis"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPenulisScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTambahPenulis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                modifier = Modifier.offset(y = (-20).dp)
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.PenuUiState,
            onpenuValueChange = viewModel::updateInsertPenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenulis()
                    navigateBack()
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
    insertUiState: InsertPenulisUiState,
    onpenuValueChange: (InsertPenulisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
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
            insertPenulisUiEvent = insertUiState.insertPenulisUiEvent,
            onValueChange = onpenuValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInput(
    insertPenulisUiEvent: InsertPenulisUiEvent,
    onValueChange: (InsertPenulisUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ){
        OutlinedTextField(
            value = insertPenulisUiEvent.id_penulis,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(id_penulis = it)) },
            label = { Text("Id Penulis") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenulisUiEvent.namaPenulis,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(namaPenulis = it)) },
            label = { Text("Nama Penulis") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenulisUiEvent.kontak,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(kontak = it)) },
            label = { Text("Kontak") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenulisUiEvent.biografi,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(biografi = it)) },
            label = { Text("Biografi") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )

        if(enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(8.dp)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(5.dp),
            thickness = 8.dp
        )
    }
}