package com.example.project_uas_036.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project_uas_036.ui.view.Buku.DestinasiDetailBuku
import com.example.project_uas_036.ui.view.Buku.DestinasiHomeBuku
import com.example.project_uas_036.ui.view.Buku.DestinasiTambahBuku
import com.example.project_uas_036.ui.view.Buku.HomeBukuScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeBuku.route,
        modifier = Modifier
    ) {
        // Home Screen(Buku)
        composable(DestinasiHomeBuku.route) {
            HomeBukuScreen(
                navigateToltemEntry = { navController.navigate(DestinasiTambahBuku.route) },
                onDetailClick = { idBuku ->
                    // Navigasi ke Detail Buku dengan ID Buku sebagai parameter
                    navController.navigate("${DestinasiDetailBuku.route}/$idBuku")
                },
                navigateToHomeKategori = {
                    navController.navigate(DestinasiHomeKategori.route)
                },
            )
        }
    }
}