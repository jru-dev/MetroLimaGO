package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaScreen(
    onNavigateBack: () -> Unit
) {
    // Coordenadas de referencia de Lima (centro aproximado)
    val centroLima = LatLng(-12.0464, -77.0428)
    
    // Estaciones ficticias cercanas
    val estacionesFicticias = listOf(
        EstacionFicticia(
            id = "F1",
            nombre = "Estación Central",
            latitud = -12.0464,
            longitud = -77.0428,
            linea = "Línea 1",
            color = Color(0xFFE30613) // Rojo
        ),
        EstacionFicticia(
            id = "F2",
            nombre = "Estación Norte",
            latitud = -12.0300,
            longitud = -77.0400,
            linea = "Línea 1",
            color = Color(0xFFE30613)
        ),
        EstacionFicticia(
            id = "F3",
            nombre = "Estación Sur",
            latitud = -12.0600,
            longitud = -77.0450,
            linea = "Línea 1",
            color = Color(0xFFE30613)
        ),
        EstacionFicticia(
            id = "F4",
            nombre = "Estación Este",
            latitud = -12.0450,
            longitud = -77.0300,
            linea = "Línea 2",
            color = Color(0xFFFFD700) // Amarillo
        ),
        EstacionFicticia(
            id = "F5",
            nombre = "Estación Oeste",
            latitud = -12.0480,
            longitud = -77.0550,
            linea = "Línea 1",
            color = Color(0xFFE30613)
        ),
        EstacionFicticia(
            id = "F6",
            nombre = "Estación Plaza",
            latitud = -12.0500,
            longitud = -77.0350,
            linea = "Línea 2",
            color = Color(0xFFFFD700)
        )
    )
    
    var estacionSeleccionada by remember { mutableStateOf<EstacionFicticia?>(null) }
    
    // Posición inicial de la cámara (centro de Lima)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(centroLima, 12f, 0f, 0f)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Mapa de Estaciones",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Volver"
                        )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Mapa de Google Maps
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        mapType = MapType.NORMAL
                    ),
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = true,
                        compassEnabled = true,
                        myLocationButtonEnabled = false
                    )
                ) {
                    // Agregar marcadores para cada estación
                    estacionesFicticias.forEach { estacion ->
                        val position = LatLng(estacion.latitud, estacion.longitud)
                        
                        Marker(
                            state = MarkerState(position = position),
                            title = estacion.nombre,
                            snippet = estacion.linea,
                            onClick = {
                                estacionSeleccionada = estacion
                                true
                            }
                        )
                    }
                }
            }
            
            // Leyenda
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Leyenda",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        LeyendaItem(Color(0xFFE30613), "Línea 1")
                        LeyendaItem(Color(0xFFFFD700), "Línea 2")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Lista de estaciones
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Estaciones Cercanas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    estacionesFicticias.forEach { estacion ->
                        EstacionMapaItem(
                            estacion = estacion,
                            isSelected = estacionSeleccionada?.id == estacion.id,
                            onClick = { 
                                estacionSeleccionada = estacion
                                // Mover la cámara a la estación seleccionada
                                cameraPositionState.position = CameraPosition(
                                    LatLng(estacion.latitud, estacion.longitud),
                                    15f,
                                    0f,
                                    0f
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Diálogo de detalles de estación
    if (estacionSeleccionada != null) {
        AlertDialog(
            onDismissRequest = { estacionSeleccionada = null },
            title = { Text(estacionSeleccionada!!.nombre) },
            text = {
                Column {
                    Text("Línea: ${estacionSeleccionada!!.linea}")
                    Text("Ubicación: ${String.format(Locale.getDefault(), "%.4f", estacionSeleccionada!!.latitud)}, ${String.format(Locale.getDefault(), "%.4f", estacionSeleccionada!!.longitud)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nota: Esta es una estación de referencia ficticia para propósitos de demostración.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
private fun LeyendaItem(color: Color, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun EstacionMapaItem(
    estacion: EstacionFicticia,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(estacion.color)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = estacion.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = estacion.linea,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Ver detalles",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

data class EstacionFicticia(
    val id: String,
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val linea: String,
    val color: Color
)
