package com.example.project_uas_036.ui.view.Buku

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_uas_036.model.Buku
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Buku.DetailBukuViewModel
import com.example.project_uas_036.ui.viewmodel.Buku.DetailUiState

object DestinasiDetailBuku : DestinasiNavigasi {
    override val route = "detailBuku"
    override val titleRes = "Detail Buku"
    const val id_buku = "idBuku"
    val routesWithArg = "$route/{$id_buku}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBukuViewScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToKategori: (String) -> Unit,

    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getMahasiswabyNim()
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d("DetailBukuViewScreen", "FloatingActionButton clicked, navigating to update screen")
                    navigateToItemUpdate()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp).offset(y = (-50).dp),
                containerColor = Color.Blue // Ganti warna sesuai keinginan
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak",
                    tint = Color.White // Ganti warna ikon jika diperlukan
                )
            }
        }
    ) { innerPadding ->
        DetailBukuStatus(
            modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState()),
            detailUiState = viewModel.bukuDetailState,
            katList = viewModel.katList,
            terbitList = viewModel.terbitList,
            tulisList = viewModel.tulisList,
            retryAction = { viewModel.getMahasiswabyNim() },
            navigateToKategori = navigateToKategori

        )
    }
}

@Composable
fun DetailBukuStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    katList: List<Kategori>,
    terbitList: List<Penerbit>,
    tulisList: List<Penulis>,
    navigateToKategori: (String) -> Unit

) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> {
            if (detailUiState.buku.id_buku.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailBuku(
                    buku = detailUiState.buku,
                    katList = katList,
                    terbitList = terbitList,
                    tulisList = tulisList,
                    modifier = modifier.fillMaxWidth(),
                    navigateToKategori = navigateToKategori

                )
            }
        }
        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailBuku(
    modifier: Modifier = Modifier,
    buku: Buku,
    katList: List<Kategori>,
    terbitList: List<Penerbit>,
    tulisList: List<Penulis>,
    navigateToKategori: (String) -> Unit

) {
    val kat = katList.find { it.idkategori == buku.id_kategori }?.namaKategori ?:""
    val terbit = terbitList.find { it.id_penerbit == buku.id_penerbit }?.namaPenerbit ?:""
    val tulis = tulisList.find { it.id_penulis == buku.id_penulis }?.namaPenulis ?:""
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "Id Buku", isinya = buku.id_buku, icon = Icons.Default.Home)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Nama", isinya = buku.nama_buku, icon = Icons.Default.Home)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Deskripsi", isinya = buku.deskripsi_buku, icon = Icons.Default.Info)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Tanggal Terbit", isinya = buku.tanggal_terbit, icon = Icons.Default.DateRange)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Id Kategori", isinya = buku.id_kategori, icon = Icons.Default.Menu)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Nama Kategori", isinya = kat, icon = Icons.Default.Menu)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Id Penerbit", isinya = buku.id_penerbit, icon = Icons.Default.MailOutline)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Nama Penerbit", isinya = terbit, icon = Icons.Default.MailOutline)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Id Penulis", isinya = buku.id_penulis, icon = Icons.Default.Person)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailMhs(judul = "Nama Penulis", isinya = tulis, icon = Icons.Default.Person)

            // Tambahkan tombol di bawah card
            Button(
                onClick = { navigateToKategori(buku.id_kategori) },
                modifier = Modifier.fillMaxWidth().offset(y = (10).dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = MaterialTheme.shapes.medium


            ) {
                Text(text = "Lihat Kategori", color = Color.White)
            }

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

