package com.proyecto.myapplication.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateEstaciones: () -> Unit,
    onNavigateRutas: () -> Unit,
    onNavigateConfiguracion: () -> Unit
) {
    val estado = viewModel.estadoServicio.collectAsState().value
    val backgroundColor = when (estado) {
        HomeViewModel.EstadoServicio.NORMAL -> Color(0xFFE8F5E9)
        HomeViewModel.EstadoServicio.DEMORAS -> Color(0xFFFFF8E1)
        HomeViewModel.EstadoServicio.FUERA_DE_SERVICIO -> Color(0xFFFFEBEE)
    }

    val cardColor = when (estado) {
        HomeViewModel.EstadoServicio.NORMAL -> Color(0xFF4CAF50)
        HomeViewModel.EstadoServicio.DEMORAS -> Color(0xFFFFC107)
        HomeViewModel.EstadoServicio.FUERA_DE_SERVICIO -> Color(0xFFF44336)
    }

    Scaffold(
        containerColor = Color(0xFFFFF6E8)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text("🚇 MetroLima GO", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color(0xFFFFA000))
            Text("Tu compañero de viaje en el Metro", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = when (estado) {
                            HomeViewModel.EstadoServicio.FUERA_DE_SERVICIO -> " Servicio Interrumpido"
                            HomeViewModel.EstadoServicio.DEMORAS -> " Servicio con demoras"
                            else -> " Servicio Normal"
                        },
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(viewModel.getMensajeEstado(), color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.White.copy(alpha = 0.6f))
                    Spacer(Modifier.height(8.dp))
                    Text(viewModel.getTiempoReparacion(), color = Color.White, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoCard("🏙️", "Estaciones", "26")
                InfoCard("⏰", "Horario", "6AM - 10PM")
                InfoCard("🎫", "Tarifa", "S/ 1.50")
            }

            Spacer(Modifier.height(24.dp))
            Text("Menú Principal", fontWeight = FontWeight.Bold, color = Color.DarkGray)

            Spacer(Modifier.height(12.dp))

            MenuButton("Planificar Ruta", "Calcula tu viaje", Color(0xFFFF9800)) { onNavigateRutas() }
            MenuButton("Ver Estaciones", "Lista completa", Color.White) { onNavigateEstaciones() }
            MenuButton("Configuración", "Ajustes de la app", Color.White) { onNavigateConfiguracion() }

            Spacer(Modifier.height(24.dp))
            Text(
                "Metro de Lima - Línea 1\nAutoridad Autónoma del Sistema Eléctrico de Transporte Masivo de Lima y Callao",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun InfoCard(icon: String, title: String, subtitle: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(4.dp)
            .width(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 22.sp)
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun MenuButton(title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
            Text(title, color = if (color == Color.White) Color.Black else Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = if (color == Color.White) Color.Gray else Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}