package com.example.project_uas_036.ui.view.Kategori

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Kategori.DetailKategoriUiState
import com.example.project_uas_036.ui.viewmodel.Kategori.DetailKategoriViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detailKategori"
    override val titleRes = "Detail Kategori"
    const val idkategori = "id_kategori"
    val routesWithArg = "$route/{$idkategori}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriViewScreen(

    navigateBack: () -> Unit,
    navigateToKategoriUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    //navigateToDetailKategori: () -> Unit, // Mendeklarasikan parameter navigateToDetailKategori
    viewModel: DetailKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailKategori.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getKategoribyid()
                }
            )
        },


    ) { innerPadding ->
        DetailKategoriStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.kategoriDetailState,
            retryAction = { viewModel.getKategoribyid() },
            navigateToKategoriUpdate = navigateToKategoriUpdate
        )
    }
}

@Composable
fun DetailKategoriStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailKategoriUiState,
    navigateToKategoriUpdate: (String) -> Unit
    //navigateToDetailKategori: () -> Unit // Menambahkan parameter navigateToDetailKategori
) {
    when (detailUiState) {
        is DetailKategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailKategoriUiState.Success -> {
            if (detailUiState.kategori.idkategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailKategori(
                    kategori = detailUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onEditClick ={
                        Log.d("DetailKategori", "Edit clicked for kategori ID: ${detailUiState.kategori.idkategori}")
                        navigateToKategoriUpdate(detailUiState.kategori.idkategori)
                    }
                )
            }
        }
        is DetailKategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailKategori(
    modifier: Modifier = Modifier,
    kategori: Kategori,
    onEditClick: () -> Unit
) {
    Log.d("ItemDetailKategori", "Displaying kategori: $kategori")
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailKategori(judul = "Id Kategori", isinya = kategori.idkategori, icon = Icons.Default.Star)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailKategori(judul = "Nama Kategori", isinya = kategori.namaKategori, icon = Icons.Default.AccountBox)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailKategori(judul = "Deskripsi", isinya = kategori.deskripsiKategori, icon = Icons.Default.Info)

            Spacer(modifier = Modifier.padding(16.dp))

            // Tambahkan tombol Edit di bawah informasi kategori
            Button(
                onClick = {
                    Log.d("DetailKategori", "Navigating to Update Kategori")
                    onEditClick() }, // Panggil fungsi callback untuk navigasi
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            ) {
                Text(text = "Edit Kategori", color = Color.White)
            }
        }
    }
}

@Composable
fun ComponentDetailKategori(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
    icon: ImageVector
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Color.Blue
            )
            Text(
                text = judul,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )


        }

        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

