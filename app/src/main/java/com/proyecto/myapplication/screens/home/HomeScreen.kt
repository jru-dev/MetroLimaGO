package com.proyecto.myapplication.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateEstaciones: () -> Unit,
    onNavigateRutas: () -> Unit,
    onNavigateConfiguracion: () -> Unit
) {
    Surface(
        color = Color(0xFFFFF3E0), // fondo crema
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo y título
            Icon(
                imageVector = Icons.Default.Train,
                contentDescription = null,
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = "MetroLima GO",
                fontSize = 22.sp,
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tu compañero de viaje en el Metro",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEF5350)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🚨 Servicio Interrumpido",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Línea 1 temporalmente fuera de servicio por mantenimiento de emergencia",
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Tiempo estimado de reparación: 45 minutos",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Fila de información
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoCard(icon = Icons.Default.Subway, title = "Estaciones", value = "26")
                InfoCard(icon = Icons.Default.AccessTime, title = "Horario", value = "6AM - 10PM")
                InfoCard(icon = Icons.Default.AttachMoney, title = "Tarifa", value = "S/ 1.50")
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = "Menú Principal",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6D4C41),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(16.dp))

            MenuButton(
                text = "Planificar Ruta",
                subtext = "Calcula tu viaje",
                color = Color(0xFFFF9800),
                icon = Icons.Default.Place,
                onClick = onNavigateRutas
            )

            MenuButton(
                text = "Ver Estaciones",
                subtext = "Lista completa",
                color = Color.White,
                icon = Icons.Default.List,
                onClick = onNavigateEstaciones
            )

            MenuButton(
                text = "Configuración",
                subtext = "Ajustes de la app",
                color = Color.White,
                icon = Icons.Default.Settings,
                onClick = onNavigateConfiguracion
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Metro de Lima - Línea 1",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun InfoCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, value: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(100.dp)
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFFFF9800))
            Spacer(Modifier.height(4.dp))
            Text(title, color = Color.Gray, fontSize = 13.sp)
            Text(value, fontWeight = FontWeight.Bold, color = Color(0xFF212121))
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    subtext: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(text, fontWeight = FontWeight.Bold, color = Color(0xFF212121))
                Text(subtext, color = Color.DarkGray, fontSize = 12.sp)
            }
        }
    }
}
