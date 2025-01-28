package com.example.project_uas_036.ui.view.Buku

import android.os.Build
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
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.customwidget.Dropdown
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Buku.InsertBukuViewModel
import com.example.project_uas_036.ui.viewmodel.Buku.InsertUiEvent
import com.example.project_uas_036.ui.viewmodel.Buku.InsertUiState
import kotlinx.coroutines.launch

object DestinasiTambahBuku : DestinasiNavigasi {
    override val route = "Tambah Buku"
    override val titleRes = "Masukkan Buku"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBukuScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTambahBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                modifier = Modifier.offset(y = (-20).dp)
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onbookValueChange = viewModel::updateInsertBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBuku()
                    navigateBack()
                }
            },
            kategoriList = viewModel.kategoriList,
            penulisList = viewModel.penulisList,
            penerbitList = viewModel.penerbitList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onbookValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    kategoriList: List<Kategori> = emptyList(),
    penulisList: List<Penulis> = emptyList(),
    penerbitList: List<Penerbit> = emptyList()
) {
    // Scroll state hanya dideklarasikan di dalam EntryBody
    //val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
        //.verticalScroll(scrollState)
        //.verticalScroll(scrollState) // Vertical scroll hanya ada di sini
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onbookValueChange,
            kategoriList = kategoriList,
            penulisList = penulisList,
            penerbitList = penerbitList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 2.dp)
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    onValueChange: (InsertUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    kategoriList: List<Kategori> = emptyList(),
    penulisList: List<Penulis> = emptyList(),
    penerbitList: List<Penerbit> = emptyList()
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),


    ){
        OutlinedTextField(
            value = insertUiEvent.id_buku,
            onValueChange = { onValueChange(insertUiEvent.copy(id_buku = it)) },
            label = { Text("Id Buku") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nama_buku,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_buku = it)) },
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_buku,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_buku = it)) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggal_terbit,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_terbit = it)) },
            label = { Text("Tanggal Terbit") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.status_buku,
            onValueChange = { onValueChange(insertUiEvent.copy(status_buku = it)) },
            label = { Text("Status") },
            modifier = Modifier.fillMaxWidth().offset(y = (-8).dp),
            enabled = enabled,
            singleLine = true
        )
        Dropdown(
            selectedValue = kategoriList.find { it.idkategori == insertUiEvent.id_kategori }?.namaKategori
                ?: "Pilih Kategori",
            options = kategoriList.map { it.namaKategori },
            label = "Kategori",
            onValueChangedEvent = { selected ->
                val selectedKategori = kategoriList.find { it.namaKategori == selected }
                onValueChange(insertUiEvent.copy(id_kategori = selectedKategori?.idkategori ?: ""))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Dropdown(
            selectedValue = penulisList.find { it.id_penulis == insertUiEvent.id_penulis }?.namaPenulis
                ?: "Pilih Penulis",
            options = penulisList.map { it.namaPenulis },
            label = "Penulis",
            onValueChangedEvent = { selected ->
                val selectedPenulis = penulisList.find { it.namaPenulis == selected }
                onValueChange(insertUiEvent.copy(id_penulis = selectedPenulis?.id_penulis ?: ""))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Dropdown(
            selectedValue = penerbitList.find { it.id_penerbit == insertUiEvent.id_penerbit }?.namaPenerbit
                ?: "Pilih Penerbit",
            options = penerbitList.map { it.namaPenerbit },
            label = "Penerbit",
            onValueChangedEvent = { selected ->
                val selectedPenerbit = penerbitList.find { it.namaPenerbit == selected }
                onValueChange(insertUiEvent.copy(id_penerbit = selectedPenerbit?.id_penerbit ?: ""))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}