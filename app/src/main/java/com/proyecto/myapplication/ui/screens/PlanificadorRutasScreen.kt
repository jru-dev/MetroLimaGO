package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyecto.myapplication.ui.components.EstacionSelector
import com.proyecto.myapplication.ui.components.RutaResultadoCard
import com.proyecto.myapplication.ui.viewmodel.PlanificadorRutasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorRutasScreen(
    onNavigateBack: () -> Unit,
    viewModel: PlanificadorRutasViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificar Ruta") },
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
                    text = "Selecciona tu ruta",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                // Selector de estación origen
                EstacionSelector(
                    label = "Estación de Origen",
                    estacion = uiState.estacionOrigen,
                    onEstacionSelected = { viewModel.selectEstacionOrigen(it) },
                    onSearchClicked = { /* TODO: Implementar búsqueda */ }
                )
            }
            
            item {
                // Botón para intercambiar estaciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { viewModel.swapEstaciones() }
                    ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Intercambiar estaciones"
                    )
                    }
                }
            }
            
            item {
                // Selector de estación destino
                EstacionSelector(
                    label = "Estación de Destino",
                    estacion = uiState.estacionDestino,
                    onEstacionSelected = { viewModel.selectEstacionDestino(it) },
                    onSearchClicked = { /* TODO: Implementar búsqueda */ }
                )
            }
            
            item {
                // Botón para calcular ruta
                Button(
                    onClick = { viewModel.calcularRuta() },
                    enabled = uiState.estacionOrigen != null && uiState.estacionDestino != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Calcular ruta"
                )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calcular Ruta")
                }
            }
            
            if (uiState.isCalculando) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Calculando la mejor ruta...")
                        }
                    }
                }
            }
            
            if (uiState.rutasCalculadas.isNotEmpty()) {
                item {
                    Text(
                        text = "Rutas Encontradas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(uiState.rutasCalculadas) { ruta ->
                    RutaResultadoCard(
                        ruta = ruta,
                        onSaveAsFavorite = { viewModel.guardarComoFavorita(ruta) },
                        onNavigate = { /* TODO: Implementar navegación paso a paso */ }
                    )
                }
            }
            
            if (uiState.error != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = uiState.error!!,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}
