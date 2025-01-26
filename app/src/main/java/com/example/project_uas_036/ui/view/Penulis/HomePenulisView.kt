package com.example.project_uas_036.ui.view.Penulis

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    override val titleRes = "Beranda Penulis"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePenulisScreen(
    navigateToPenulisEntry: () -> Unit,
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
            CoustumeTopAppBar(
                title = DestinasiHomePenulis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPenulis()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPenulisEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
    ){ innerPadding ->
        HomeStatus(
            homeUiState = viewModel.PenuUiState,
            retryAction = { viewModel.getPenulis() },
            modifier = Modifier.padding(innerPadding),
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
                        imageVector = Icons.Default.Add, // Ganti dengan ikon edit (jika ada)
                        contentDescription = "Update Penulis",
                    )
                }
                Text(
                    text = penulis.namaPenulis,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = penulis.biografi,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = penulis.kontak,
                style = MaterialTheme.typography.titleMedium
            )


        }
    }
}