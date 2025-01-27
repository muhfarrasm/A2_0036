package com.example.project_uas_036.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_uas_036.ui.navigation.DestinasiNavigasi

object DestinasiHomePage : DestinasiNavigasi {
    override val route = "homepage"
    override val titleRes = "Home Page"
}
@Composable
fun LibraryAppHomepage(
    navigateToHomeBuku: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Latar belakang gradasi dengan lengkungan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4C6FF9), // Warna biru gelap
                            Color(0xFF6A8FFD), // Warna biru terang
                            Color(0xFFB3C7FF)  // Warna biru muda
                        )
                    ),
                    shape = CustomTopShape() // Bentuk kustom untuk lengkungan atas
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "LIBRARY EDUCATION",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Gambar Ilustrasi
            Image(
                //painter = painterResource(id = R.drawable.toga_buku), // Ganti dengan ilustrasi
                painter = painterResource(id = com.example.project_uas_036.R.drawable.toga_buku),
                contentDescription = "Illustration",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))
            // Deskripsi
            Text(
                text = "growth mindset start from read",
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(70.dp))
            Button(
                onClick = navigateToHomeBuku,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .width(200.dp) // Atur lebar tombol
                    .height(50.dp) // Atur tinggi tombol
            ) {
                Text(text = "Let's Go", fontSize = 20.sp,)
            }
        }
    }
}

// Custom Shape untuk bagian atas melengkung
@Composable
fun CustomTopShape(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val path = Path().apply {
                moveTo(0f, size.height * 0.2f) // Titik awal) // Dimulai dari 20% tinggi
                cubicTo(
                    size.width * 0.2f, 0f, // Kontrol kiri
                    size.width * 0.8f, 0f, // Kontrol kanan
                    size.width, size.height * 0.2f// Akhir lengkungan
                )
                lineTo(size.width, size.height) // Lurus ke bawah
                lineTo(0f, size.height) // Lurus ke kiri
                close()
            }
            return Outline.Generic(path)
        }
    }
}
