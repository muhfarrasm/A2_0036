package com.example.project_uas_036.ui.navigation

import android.os.Build
import android.util.Log
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
import com.example.project_uas_036.ui.view.Kategori.DestinasiDetailKategori
import com.example.project_uas_036.ui.view.Kategori.DestinasiHomeKategori
import com.example.project_uas_036.ui.view.Kategori.DestinasiTambahKategori
import com.example.project_uas_036.ui.view.Kategori.DestinasiUpdateKategori
import com.example.project_uas_036.ui.view.Kategori.DetailKategoriViewScreen
import com.example.project_uas_036.ui.view.Kategori.EntryKategoriScreen
import com.example.project_uas_036.ui.view.Kategori.HomeKategoriScreen
import com.example.project_uas_036.ui.view.Kategori.UpdateKategoriScreen

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
                navigateToHomeKategori = { navController.navigate(DestinasiHomeKategori.route)}

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

        //          KATEGORI

        // Home Screen Kategori
        composable(DestinasiHomeKategori.route) {
            HomeKategoriScreen(
                navigateToKategoriEntry = { navController.navigate(DestinasiTambahKategori.route) },
                navigateBack = { navController.popBackStack() },
                onDetailClick = { idkategori ->
                    navController.navigate("${DestinasiDetailKategori.route}/$idkategori")
                }
            )
        }

        // Input Data (Tambah Kategori)
        composable(DestinasiTambahKategori.route) {
            EntryKategoriScreen(navigateBack = {
                navController.navigate(DestinasiHomeKategori.route) {
                    // Menghapus semua halaman sebelumnya agar back ke Home langsung ke halaman utama
                    popUpTo(DestinasiHomeKategori.route) { inclusive = true }
                }
            })
        }

        // Detail Kategori (menerima ID kategori sebagai argument)
        composable(
            DestinasiDetailKategori.routesWithArg,

            arguments = listOf(navArgument(DestinasiDetailKategori.idkategori) {
                type = NavType.StringType // Pastikan tipe argument sesuai
            })
        ) {
            val idKategori = it.arguments?.getString(DestinasiDetailKategori.idkategori)
            idKategori?.let { idkategori ->
                DetailKategoriViewScreen(
                    navigateToKategoriUpdate = {
                        Log.d("DetailKategori", "Navigating to update kategori: $idkategori")
                        navController.navigate("${DestinasiUpdateKategori.route}/$idkategori")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiHomeKategori.route) {
                            // Kembali ke halaman Home
                            popUpTo(DestinasiHomeKategori.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Update Kategori
        composable(
            DestinasiUpdateKategori.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateKategori.id_kategori) {
                type = NavType.StringType // Pastikan tipe argument sesuai
            })
        ) {
            Log.d("Navigasi", "Masuk ke rute dengan idKategori: ${it.arguments?.getString(DestinasiUpdateKategori.id_kategori)}")
            val idkategori = it.arguments?.getString(DestinasiUpdateKategori.id_kategori)
            idkategori?.let { idkat ->
                UpdateKategoriScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }?: Log.e("Navigasi", "idKategori tidak diterima dengan benar!")
        }

    }
}