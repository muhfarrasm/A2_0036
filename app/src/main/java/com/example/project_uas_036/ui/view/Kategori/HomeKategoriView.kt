package com.example.project_uas_036.ui.view.Kategori

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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
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
import com.example.project_uas_036.model.Kategori
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Kategori.HomeKategoriUiState
import com.example.project_uas_036.ui.viewmodel.Kategori.HomeKategoriViewModel

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "homekategori"
    override val titleRes = "Home Kategori"

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKategoriScreen(
    navigateToKategoriEntry: () -> Unit,
    navigateToHomePenulis: () -> Unit,
    navigateToHomePenerbit: () -> Unit,
    navigateToHomeBuku: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                HeaderKategori(
                    namaApp = "EduLibApps",
                    ID = R.drawable.toga_buku
                )
                Spacer(modifier = Modifier.height(10.dp))
                CoustumeTopAppBar(
                    title = DestinasiHomeKategori.titleRes,
                    canNavigateBack = false,
                    navigateUp = navigateBack,
                    scrollBehavior = scrollBehavior,
                    onRefresh = {
                        viewModel.getKategori()
                    }
                )
            }

        },
        // Navbar with buttons
        bottomBar = {
            BottomNavBar(
                navigateToKategoriEntry = navigateToKategoriEntry,
                navigateToHomeBuku = navigateToHomeBuku,
                navigateToHomePenulis = navigateToHomePenulis,
                navigateToHomePenerbit = navigateToHomePenerbit
            )
        }
    ){ innerPadding ->
        HomeStatus(
            homeUiState = viewModel.KateUiState,
            retryAction = { viewModel.getKategori() },
            modifier = Modifier.padding(innerPadding) // Menggunakan innerPadding untuk menyesuaikan konten utama
                .padding(top = 8.dp) // Menambahkan sedikit jarak atas agar tidak tertutup topBar
                .background(Color(0xFF9FC5E8)),
            onDetailClick = { kategoriId ->
                onDetailClick(kategoriId) // Memanggil onDetailClick yang sesuai
            },
            onDeleteClick = { kategori ->
                viewModel.deleteKategori(kategori.idkategori)
                viewModel.getKategori()
            }
        )
    }
}

@Composable
fun BottomNavBar(
    navigateToKategoriEntry: () -> Unit,
    navigateToHomePenulis: () -> Unit,
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
                    onClick = navigateToKategoriEntry,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Kategori",
                        tint = Color.White
                    )
                }
                Text(text = "Tambah Kategori", color = Color.White, style = MaterialTheme.typography.labelMedium)
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
                Text(text = "Home Buku", color = Color.White, style = MaterialTheme.typography.labelMedium)
            }

            // Third IconButton with text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = navigateToHomePenulis,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Home Penulis",
                        tint = Color.White
                    )
                }
                Text(text = "Home Penulis", color = Color.White, style = MaterialTheme.typography.labelMedium)
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
                Text(text = "Home Penerbit", color = Color.White, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}




@Composable
fun HeaderKategori(
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
    homeUiState: HomeKategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Kategori) -> Unit = {},
){
    when (homeUiState) {
        is HomeKategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeKategoriUiState.Success -> {
            if (homeUiState.kategori.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                KategoriLayout(
                    kategori = homeUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { kategori ->
                        onDetailClick(kategori.idkategori) // Memastikan hanya id_kategori yang diteruskan
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        }

        is HomeKategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())

        // Handle any unexpected cases with an else branch
        else -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Unknown state")
            }
        }
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
fun KategoriLayout(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kategori) -> Unit,
    onDeleteClick: (Kategori) -> Unit = {},
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategori) { kontak ->
            println("Clicked kategori ID: ${kontak.idkategori}")
            kategoriCard(
                kategori = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        println("Detail kategori: ${kontak.idkategori}")
                        onDetailClick(kontak)
                    },
                onDeleteClick = {
                    println("Deleting kategori: ${kontak.idkategori}")
                    onDeleteClick(kontak)
                }
            )
        }
    }
}

@Composable

fun kategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {}
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
            // Row for ID Kategori and Delete Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star, // Replace with appropriate icon
                    contentDescription = "ID Kategori",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = kategori.idkategori,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Kategori",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Row for Nama Kategori
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox, // Replace with appropriate icon
                    contentDescription = "Nama Kategori",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            // Row for Deskripsi Kategori
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info, // Replace with appropriate icon
                    contentDescription = "Deskripsi Kategori",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = kategori.deskripsiKategori,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
