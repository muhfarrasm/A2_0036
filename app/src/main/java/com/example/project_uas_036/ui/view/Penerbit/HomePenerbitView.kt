package com.example.project_uas_036.ui.view.Penerbit

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_uas_036.R
import com.example.project_uas_036.model.Penerbit
import com.example.project_uas_036.ui.PenyediaViewModel
import com.example.project_uas_036.ui.customwidget.CoustumeTopAppBar
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi
import com.example.project_uas_036.ui.viewmodel.Penerbit.HomePenerbitUiState
import com.example.project_uas_036.ui.viewmodel.Penerbit.HomePenerbitViewModel


object DestinasiHomePenerbit : DestinasiNavigasi {
    override val route = "homepenerbit"
    override val titleRes = "Beranda Penerbit"

}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomePenerbitScreen(
    navigateToPenerbitEntry: () -> Unit,
    // navigateToHomePenerbit: () -> Unit,
    navigateToHomeBuku: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToUpdatePenerbit: (String) -> Unit,
    viewModel: HomePenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

            Column{

                Spacer(modifier = Modifier.height(21.dp))
                Header(
                    namaApp = "Ayo Membaca",
                    ID = R.drawable.fae282ab40e04da8eed627a767815dc8
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Button(
                        onClick = navigateToPenerbitEntry,
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Text(text = "Tambah Penerbit")
                    }
                    Button(
                        onClick = navigateToHomeBuku, // Meneruskan parameter navigateToDetailKategori
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Text(text = "Menu Buku")
                    }
                }

                Spacer(modifier = Modifier)
                CoustumeTopAppBar(
                    title = DestinasiHomePenerbit.titleRes,
                    canNavigateBack = false,
                    scrollBehavior = scrollBehavior,
                    onRefresh = {
                        viewModel.getPenerbit()
                    }
                )
            }

        },

        ){ innerPadding ->
        HomeStatus(
            homeUiState = viewModel.PeneUiState,
            retryAction = { viewModel.getPenerbit() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = navigateToUpdatePenerbit,
        )
    }
}
@Composable
fun Header(
    namaApp: String,
    ID: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF6700ff),
                        Color(0xFF6700ff)
                    )
                )
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
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "ikon",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))


            }
        }
    }
}
@Composable
fun HomeStatus(
    homeUiState: HomePenerbitUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,

    ){
    when (homeUiState) {
        is HomePenerbitUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomePenerbitUiState.Success ->
            if (homeUiState.penerbit.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data penerbit")
                }
            } else {
                PenerbitLayout(
                    penerbit = homeUiState.penerbit,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_penerbit)
                    },

                    )
            }
        is HomePenerbitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun PenerbitLayout(
    penerbit: List<Penerbit>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penerbit) -> Unit,

    ){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penerbit){kontak ->
            PenerbitCard(
                penerbit = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(kontak)},
            )
        }
    }
}

@Composable
fun PenerbitCard(
    penerbit: Penerbit,
    modifier: Modifier = Modifier,

    ) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                    text = penerbit.id_penerbit,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = penerbit.namaPenerbit,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = penerbit.teleponPenerbit,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = penerbit.alamatPenerbit,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

