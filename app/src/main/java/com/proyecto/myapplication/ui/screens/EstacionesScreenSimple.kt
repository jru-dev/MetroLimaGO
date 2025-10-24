package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.myapplication.data.EstacionesData
import com.proyecto.myapplication.data.Estacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstacionesScreenSimple(
    onNavigateBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var lineaSeleccionada by remember { mutableStateOf("Todas") }
    
    val estacionesFiltradas = remember(query, lineaSeleccionada) {
        val estaciones = if (lineaSeleccionada == "Todas") {
            EstacionesData.todasLasEstaciones
        } else {
            EstacionesData.getEstacionesPorLinea(lineaSeleccionada)
        }
        
        if (query.isBlank()) {
            estaciones
        } else {
            estaciones.filter { 
                it.nombre.contains(query, ignoreCase = true) ||
                it.distrito.contains(query, ignoreCase = true)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estaciones del Metro") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
                // Barra de búsqueda
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Buscar estación...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            item {
                // Filtros por línea
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = lineaSeleccionada == "Todas",
                        onClick = { lineaSeleccionada = "Todas" },
                        label = { Text("Todas") }
                    )
                    FilterChip(
                        selected = lineaSeleccionada == "Línea 1",
                        onClick = { lineaSeleccionada = "Línea 1" },
                        label = { Text("Línea 1") }
                    )
                    FilterChip(
                        selected = lineaSeleccionada == "Línea 2",
                        onClick = { lineaSeleccionada = "Línea 2" },
                        label = { Text("Línea 2") }
                    )
                }
            }
            
            item {
                Text(
                    text = "Estaciones (${estacionesFiltradas.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(estacionesFiltradas) { estacion ->
                EstacionCardSimple(estacion = estacion)
            }
        }
    }
}

@Composable
fun EstacionCardSimple(estacion: Estacion) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicador de línea
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp)
                    ) {
                        // Círculo de color de la línea
                        androidx.compose.foundation.shape.CircleShape
                    }
                }
            }
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = estacion.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = estacion.linea,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = estacion.distrito,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Estado de la estación
            if (estacion.linea == "Línea 2") {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFD700).copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = "En construcción",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "Operativa",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
