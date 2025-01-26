package com.example.project_uas_036.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project_uas_036.ui.view.Buku.DestinasiDetailBuku
import com.example.project_uas_036.ui.view.Buku.DestinasiHomeBuku
import com.example.project_uas_036.ui.view.Buku.DestinasiTambahBuku
import com.example.project_uas_036.ui.view.Buku.DestinasiUpdateBuku
import com.example.project_uas_036.ui.view.Buku.DetailBukuViewScreen
import com.example.project_uas_036.ui.view.Buku.EntryBukuScreen
import com.example.project_uas_036.ui.view.Buku.HomeBukuScreen
import com.example.project_uas_036.ui.view.Buku.UpdateBukuScreen

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

            )
        }

        // Input Data (Tambah Buku)
        composable(DestinasiTambahBuku.route) {
            EntryBukuScreen(navigateBack = {
                navController.navigate(DestinasiHomeBuku.route) {
                    // Menghapus semua halaman sebelumnya agar back ke Home langsung ke halaman utama
                    popUpTo(DestinasiHomeBuku.route) { inclusive = true }
                }
            })
        }

        // Detail Buku (menerima ID buku sebagai argument)
        composable(
            DestinasiDetailBuku.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailBuku.id_buku) {
                type = NavType.StringType // Pastikan tipe argument sesuai
            })
        ) {
            val idBuku = it.arguments?.getString(DestinasiDetailBuku.id_buku)
            idBuku?.let { id_buku ->
                DetailBukuViewScreen(
                    navigateToItemUpdate = {
                        // Navigasi ke Update Buku dengan ID Buku sebagai parameter
                        navController.navigate("${DestinasiUpdateBuku.route}/$id_buku")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiHomeBuku.route) {
                            // Kembali ke halaman Home
                            popUpTo(DestinasiHomeBuku.route) { inclusive = true }
                        }
                    },

                )
            }
        }


        // Update Buku (menerima ID buku sebagai argument)
        composable(
            DestinasiUpdateBuku.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateBuku.id_buku) {
                type = NavType.StringType // Pastikan tipe argument sesuai
            })
        ) {
            val idBuku = it.arguments?.getString(DestinasiUpdateBuku.id_buku)
            idBuku?.let { idbook ->
                UpdateBukuScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

    }
}