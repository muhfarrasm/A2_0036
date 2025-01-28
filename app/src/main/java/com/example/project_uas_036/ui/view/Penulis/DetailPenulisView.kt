package com.example.project_uas_036.ui.view.Penulis

import android.os.Build
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Penulis.DetailPenulisUiState
import com.example.project_uas_036.ui.viewmodel.Penulis.DetailPenulisViewModel

object DestinasiDetailPenulis : DestinasiNavigasi {
    override val route = "detailPenulis"
    override val titleRes = "Detail Penulis"
    const val id_penulis = "id_penulis"
    val routesWithArg = "$route/{$id_penulis}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenulisViewScreen(
    navigateBack: () -> Unit,
//    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: DetailPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailPenulis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getpenulisbyId()
                }
            )
        },

    ) { innerPadding ->
        DetailBukuStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.penulisDetailState,
            retryAction = { viewModel.getpenulisbyId() },

            )
    }
}

@Composable
fun DetailBukuStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailPenulisUiState,

    ) {
    when (detailUiState) {
        is DetailPenulisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailPenulisUiState.Success -> {
            if (detailUiState.penu.id_penulis.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailPenulis(
                    penulis = detailUiState.penu,
                    modifier = modifier.fillMaxWidth(),

                    )
            }
        }
        is DetailPenulisUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailPenulis(
    modifier: Modifier = Modifier,
    penulis: Penulis,

    ) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "Id Buku", isinya = penulis.id_penulis, icon = Icons.Default.Star)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailMhs(judul = "Nama", isinya = penulis.namaPenulis, icon = Icons.Default.Person)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailMhs(judul = "Deskripsi", isinya = penulis.kontak, icon = Icons.Default.Info)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailMhs(judul = "Biografi", isinya = penulis.biografi, icon = Icons.Default.PlayArrow)

        }
    }
}

@Composable
fun ComponentDetailMhs(
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



