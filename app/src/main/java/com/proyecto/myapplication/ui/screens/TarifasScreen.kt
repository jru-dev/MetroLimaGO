package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarifasScreen(
    onNavigateBack: () -> Unit
) {
    val tarifas = listOf(
        TarifaInfo("Tarifa Única", "S/ 1.50", "Válida para un viaje en cualquier dirección", Icons.Filled.Info),
        TarifaInfo("Tarifa Reducida", "S/ 0.75", "Estudiantes, adultos mayores y personas con discapacidad", Icons.Filled.Info),
        TarifaInfo("Tarjeta Integrada", "S/ 1.50", "Válida para Metro, Metropolitano y Corredores", Icons.Filled.Info)
    )
    
    val metodosPago = listOf(
        MetodoPago("Tarjeta de Recarga", "Recarga tu tarjeta en las estaciones", Icons.Filled.Info),
        MetodoPago("Tarjeta de Débito/Crédito", "Pago con tarjeta en las estaciones", Icons.Filled.Info),
        MetodoPago("Efectivo", "Pago en efectivo en las taquillas", Icons.Filled.Info),
        MetodoPago("App Móvil", "Recarga desde la aplicación móvil", Icons.Filled.Phone)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tarifas y Pagos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Tarifas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(tarifas) { tarifa ->
                TarifaCard(tarifa = tarifa)
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Métodos de Pago",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(metodosPago) { metodo ->
                MetodoPagoCard(metodo = metodo)
            }
        }
    }
}

@Composable
fun TarifaCard(tarifa: TarifaInfo) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = tarifa.icon,
                contentDescription = tarifa.titulo,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarifa.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tarifa.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = tarifa.precio,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MetodoPagoCard(metodo: MetodoPago) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = metodo.icon,
                contentDescription = metodo.nombre,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = metodo.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = metodo.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class TarifaInfo(
    val titulo: String,
    val precio: String,
    val descripcion: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class MetodoPago(
    val nombre: String,
    val descripcion: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

