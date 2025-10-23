package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyecto.myapplication.ui.components.MetroCard
import com.proyecto.myapplication.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToEstaciones: () -> Unit,
    onNavigateToRutas: () -> Unit,
    onNavigateToConfiguracion: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "MetroLima GO",
                        fontWeight = FontWeight.Bold
                    ) 
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
                // Banner principal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡Bienvenido a MetroLima GO!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Planifica tu viaje en el Metro de Lima",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            item {
                Text(
                    text = "Servicios",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                MetroCard(
                    title = "Estaciones",
                    description = "Consulta información de todas las estaciones",
                    icon = "🚇",
                    onClick = onNavigateToEstaciones
                )
            }
            
            item {
                MetroCard(
                    title = "Planificar Ruta",
                    description = "Encuentra la mejor ruta para tu destino",
                    icon = "🗺️",
                    onClick = { /* TODO: Navegar a planificador */ }
                )
            }
            
            item {
                MetroCard(
                    title = "Rutas Guardadas",
                    description = "Consulta tus rutas favoritas",
                    icon = "⭐",
                    onClick = onNavigateToRutas
                )
            }
            
            item {
                MetroCard(
                    title = "Alertas del Metro",
                    description = "Información sobre retrasos y mantenimiento",
                    icon = "⚠️",
                    onClick = { /* TODO: Navegar a alertas */ }
                )
            }
            
            item {
                MetroCard(
                    title = "Configuración",
                    description = "Personaliza tu experiencia",
                    icon = "⚙️",
                    onClick = onNavigateToConfiguracion
                )
            }
            
            item {
                // Información del servicio
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Información del Servicio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Horario: 5:00 AM - 11:00 PM",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Frecuencia: 3-5 minutos",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Estado: Operativo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
