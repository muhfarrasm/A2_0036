package com.example.project_uas_036.ui.view.Penulis

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_uas_036.R
import com.example.project_uas_036.model.Penulis
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Penulis.HomePenulisUiState
import com.example.project_uas_036.ui.viewmodel.Penulis.HomePenulisViewModel

object DestinasiHomePenulis : DestinasiNavigasi {
    override val route = "homepenulis"
    override val titleRes = "Home Penulis"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePenulisScreen(
    navigateToPenulisEntry: () -> Unit,
    navigateToHomeKategori: () -> Unit,
    navigateToHomePenerbit: () -> Unit,
    navigateToHomeBuku: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPenulisClick: (String) -> Unit = {},
    navigateToUpdatePenulis: (String) -> Unit,
    viewModel: HomePenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column (
                modifier = Modifier
                    .background(Color(0xFF9FC5E8)) // Ganti dengan warna yang diinginkan
                    .fillMaxWidth() // Membuat column mengisi lebar penuh
            ){
                Spacer(modifier = Modifier.height(10.dp)) // Memberikan jarak atas
                HeaderPenulis(
                    namaApp = "EduLibApps",
                    ID = R.drawable.toga
                )
                Spacer(modifier = Modifier.height(10.dp))
                CoustumeTopAppBar(
                    title = DestinasiHomePenulis.titleRes,
                    canNavigateBack = false,
                    navigateUp = navigateBack,
                    scrollBehavior = scrollBehavior,
                    onRefresh = {
                        viewModel.getPenulis()
                    }
                )
            }

        },
        // Navbar with buttons
        bottomBar = {
            BottomNavBar(
                navigateToPenulisEntry = navigateToPenulisEntry,
                navigateToHomeBuku = navigateToHomeBuku,
                navigateToHomeKategori = navigateToHomeKategori,
                navigateToHomePenerbit = navigateToHomePenerbit
            )
        }

    ){ innerPadding ->
        HomeStatus(
            homeUiState = viewModel.PenuUiState,
            retryAction = { viewModel.getPenulis() },
            modifier = Modifier.padding(innerPadding) // Menggunakan innerPadding untuk menyesuaikan konten utama
                .padding(top = 8.dp) // Menambahkan sedikit jarak atas agar tidak tertutup topBar
                .background(Color(0xFF9FC5E8)),
            onDetailPenulisClick = { penulisId ->
                onDetailPenulisClick(penulisId) // Memanggil onDetailClick yang sesuai
            },
            onDeleteClick = { penulis ->
                viewModel.deletePenulis(penulis.id_penulis)
                viewModel.getPenulis()
            },
            onUpdateClick = { penulis ->
                navigateToUpdatePenulis(penulis.id_penulis) // Use navigateToUpdatePenulis for updates
            }


        )
    }
}
@Composable
fun BottomNavBar(
    navigateToPenulisEntry: () -> Unit,
    navigateToHomeKategori: () -> Unit,
    navigateToHomePenerbit: () -> Unit,
    navigateToHomeBuku: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF001880),
                        Color(0xFF3055A3)
                    )
                ),
                shape = CustomBottom()
            )
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // First IconButton with text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = navigateToPenulisEntry,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Penulis",
                        tint = Color.White
                    )
                }
                Text(text = "Tambah Penulis", color = Color.White, style = MaterialTheme.typography.labelLarge)
            }

            // Second IconButton with text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = navigateToHomeBuku,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Buku",
                        tint = Color.White
                    )
                }
                Text(text = "Home Buku", color = Color.White, style = MaterialTheme.typography.labelLarge)
            }

            // Third IconButton with text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = navigateToHomeKategori,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Home Kategori",
                        tint = Color.White
                    )
                }
                Text(text = "Home Kategori", color = Color.White, style = MaterialTheme.typography.labelLarge)
            }

            // Fourth IconButton with text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = navigateToHomePenerbit,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "Home Penerbit",
                        tint = Color.White
                    )
                }
                Text(text = "Home Penerbit", color = Color.White, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}




@Composable
fun HeaderPenulis(
    namaApp: String,
    ID: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 1.dp, end = 1.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF001880),
                        Color(0xFF3055A3)
                    )
                ),
                shape = CustomTop()

            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White.copy(alpha = 0.3f))
            ) {
                Image(
                    painter = painterResource(ID),
                    contentDescription = "profil",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(35.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = namaApp,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTop(): Shape {
    return RoundedCornerShape(
        topStart = 20.dp,  // Lengkungan pada pojok kiri atas
        topEnd = 20.dp,    // Lengkungan pada pojok kanan atas
        bottomStart = 20.dp, // Tidak ada lengkungan di pojok kiri bawah
        bottomEnd = 20.dp    // Tidak ada lengkungan di pojok kanan bawah
    )
}

@Composable
fun CustomBottom(): Shape {
    return RoundedCornerShape(
        topStart = 20.dp,  // Lengkungan pada pojok kiri atas
        topEnd = 20.dp,    // Lengkungan pada pojok kanan atas
        bottomStart = 0.dp, // Tidak ada lengkungan di pojok kiri bawah
        bottomEnd = 0.dp    // Tidak ada lengkungan di pojok kanan bawah
    )
}

@Composable
fun HomeStatus(
    homeUiState: HomePenulisUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPenulisClick: (String) -> Unit,
    onDeleteClick: (Penulis) -> Unit = {},
    onUpdateClick: (Penulis) -> Unit = {},
){
    when (homeUiState) {
        is HomePenulisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomePenulisUiState.Success ->
            if (homeUiState.penulis.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                PenulisLayout(
                    penulis = homeUiState.penulis,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { penulis ->
                        onDetailPenulisClick(penulis.id_penulis) // Memastikan hanya id_penulis yang diteruskan
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onUpdateClick = { onUpdateClick(it) }


                )
            }
        is HomePenulisUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.fae282ab40e04da8eed627a767815dc8),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Image(
            painter = painterResource(id = R.drawable.fae282ab40e04da8eed627a767815dc8),
            contentDescription = ""
        )
    }
}

@Composable
fun PenulisLayout(
    penulis: List<Penulis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penulis) -> Unit,
    onDeleteClick: (Penulis) -> Unit = {},
    onUpdateClick: (Penulis) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penulis) { kontak ->
            println("Clicked kategori ID: ${kontak.id_penulis}")
            PenulisCard(
                penulis = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        println("Detail kategori: ${kontak.id_penulis}")
                        onDetailClick(kontak)
                    },
                onDeleteClick = {
                    println("Deleting kategori: ${kontak.id_penulis}")
                    onDeleteClick(kontak)
                },
                onUpdateClick = { // Tambahkan ini
                    onUpdateClick(kontak)
                }

            )
        }
    }
}

@Composable
fun PenulisCard(
    penulis: Penulis,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penulis) -> Unit = {},
    onUpdateClick: (Penulis) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,

            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = penulis.id_penulis,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(penulis) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

                IconButton(onClick = { onUpdateClick(penulis) }) { // Tambahkan ini
                    Icon(
                        imageVector = Icons.Default.Edit, // Ganti dengan ikon edit (jika ada)
                        contentDescription = "Update Penulis",
                    )
                }
                Spacer(Modifier.weight(0.7f))
                Column{
                    Text(
                        text = penulis.namaPenulis,
                        style = MaterialTheme.typography.titleMedium,

                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = penulis.biografi,
                        style = MaterialTheme.typography.titleMedium
                    )

                }

            }
            Text(
                text = penulis.kontak,
                style = MaterialTheme.typography.titleMedium
            )




        }
    }
}