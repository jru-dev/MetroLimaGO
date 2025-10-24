package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
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
    var estacionSeleccionada by remember { mutableStateOf<Estacion?>(null) }

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
                    containerColor = Color(0xFFE30613),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
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
                EstacionCardSimple(
                    estacion = estacion,
                    onClick = { estacionSeleccionada = estacion }
                )
            }
        }
    }

    // Diálogo de información de estación
    estacionSeleccionada?.let { estacion ->
        AlertDialog(
            onDismissRequest = { estacionSeleccionada = null },
            title = {
                Column {
                    Text(
                        text = estacion.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = estacion.linea,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE30613)
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoRow(
                        icon = Icons.Filled.Info,
                        label = "Distrito",
                        value = estacion.distrito
                    )

                    InfoRow(
                        icon = Icons.Filled.Info,
                        label = "Orden",
                        value = "Estación ${estacion.orden}"
                    )

                    if (estacion.linea == "Línea 2") {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFD700).copy(alpha = 0.2f)
                            )
                        ) {
                            Text(
                                text = "⚠️ Esta estación está en construcción",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { estacionSeleccionada = null }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun EstacionCardSimple(
    estacion: Estacion,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
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
                    .size(40.dp)
                    .padding(end = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = MaterialTheme.shapes.small,
                    color = when (estacion.linea) {
                        "Línea 1" -> Color(0xFFE30613)
                        "Línea 2" -> Color(0xFFFFD700)
                        else -> MaterialTheme.colorScheme.primary
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = estacion.orden.toString(),
                            color = if (estacion.linea == "Línea 2") Color.Black else Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
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
                    color = Color(0xFFE30613)
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

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}