package com.example.project_uas_036.ui.view.Penulis

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.example.project_uas_036.ui.viewmodel.Penulis.UpdatePenulisViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePenulis : DestinasiNavigasi {
    override val route = "Update Penulis"
    override val titleRes = "Update Penulis"
    const val id_penulis = "id_penulis"
    val routesWithArg = "$route/{$id_penulis}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePenulisScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdatePenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdatePenulis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        Column (modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
        ){
            Spacer(modifier = Modifier.height(20.dp)) // Tambahkan Spacer di sini

            EntryBody(
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
                insertUiState = viewModel.updatePenulisUiState,
                onpenuValueChange = viewModel::updateInsertPenulisState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updatePenulis()
                        delay(600)
                        withContext(Dispatchers.Main){
                            onNavigate()
                        }
                    }
                }
            )
        }

    }
}