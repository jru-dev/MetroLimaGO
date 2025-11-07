package com.proyecto.myapplication.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
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
                    EstacionCardClickable(
                        estacion = estacion,
                        onClick = { estacionSeleccionada = estacion }
                    )
                }
            }

            // Dialog con información de la estación
            if (estacionSeleccionada != null) {
                EstacionDetailDialog(
                    estacion = estacionSeleccionada!!,
                    onDismiss = { estacionSeleccionada = null }
                )
            }
        }
    }
}

@Composable
fun EstacionCardClickable(
    estacion: Estacion,
    onClick: () -> Unit
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (estacion.linea) {
                            "Línea 1" -> Color(0xFFE30613)
                            "Línea 2" -> Color(0xFFFFD700)
                            else -> MaterialTheme.colorScheme.primary
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = estacion.orden.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

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
                    color = when (estacion.linea) {
                        "Línea 1" -> Color(0xFFE30613)
                        "Línea 2" -> Color(0xFFFFD700)
                        else -> MaterialTheme.colorScheme.primary
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstacionDetailDialog(
    estacion: Estacion,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    // Obtener el ID del recurso de la imagen basado en el ID de la estación
    val imageResId = remember(estacion.id) {
        val resName = estacion.id.lowercase() // est_001, est_002, etc.
        context.resources.getIdentifier(resName, "drawable", context.packageName)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Header con imagen de fondo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // Verificar si existe la imagen
                    if (imageResId != 0) {
                        // Cargar imagen real de la estación
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Imagen de ${estacion.nombre}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Overlay con gradiente para mejorar legibilidad del texto
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.3f),
                                            Color.Black.copy(alpha = 0.7f)
                                        ),
                                        startY = 0f,
                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        )
                    } else {
                        // Fallback: Imagen de fondo con gradiente (placeholder)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            when (estacion.linea) {
                                                "Línea 1" -> Color(0xFFE30613)
                                                "Línea 2" -> Color(0xFFFFD700)
                                                else -> MaterialTheme.colorScheme.primary
                                            },
                                            when (estacion.linea) {
                                                "Línea 1" -> Color(0xFFE30613).copy(alpha = 0.6f)
                                                "Línea 2" -> Color(0xFFFFD700).copy(alpha = 0.6f)
                                                else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                            }
                                        )
                                    )
                                )
                        )

                        // Icono de metro placeholder
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                tint = Color.White.copy(alpha = 0.3f)
                            )
                        }
                    }

                    // Botón de cerrar
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Cerrar",
                            tint = Color.White
                        )
                    }

                    // Nombre de la estación en la parte inferior
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = estacion.nombre,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = estacion.linea,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }

                // Contenido de información
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Información básica
                    InfoRow(
                        icon = Icons.Filled.Place,
                        title = "Distrito",
                        value = estacion.distrito
                    )

                    Divider()

                    InfoRow(
                        icon = Icons.Filled.Info,
                        title = "Número de Orden",
                        value = estacion.orden.toString()
                    )

                    Divider()

                    // Estado de operación
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (estacion.linea == "Línea 2")
                                Icons.Filled.Build
                            else
                                Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = if (estacion.linea == "Línea 2")
                                Color(0xFFFFD700)
                            else
                                Color(0xFF4CAF50),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Estado",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (estacion.linea == "Línea 2")
                                    "En Construcción"
                                else
                                    "Operativa",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Divider()

                    // Descripción adicional
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Información Adicional",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Estación ${estacion.nombre} del Metro de Lima, ubicada en el distrito de ${estacion.distrito}. ${
                                    if (estacion.linea == "Línea 2")
                                        "Esta estación se encuentra actualmente en construcción como parte del proyecto de expansión del Metro de Lima."
                                    else
                                        "Esta estación se encuentra operativa y en funcionamiento normal."
                                }",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    // Botón de acción
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (estacion.linea) {
                                "Línea 1" -> Color(0xFFE30613)
                                "Línea 2" -> Color(0xFFFFD700)
                                else -> MaterialTheme.colorScheme.primary
                            }
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Planificar Ruta desde aquí")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}