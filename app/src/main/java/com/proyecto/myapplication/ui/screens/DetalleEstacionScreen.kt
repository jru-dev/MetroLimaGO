package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyecto.myapplication.data.FavoritosManagerSingleton
import com.proyecto.myapplication.data.ServiciosData
import com.proyecto.myapplication.data.model.TipoServicio
import com.proyecto.myapplication.ui.components.InfoCard
import com.proyecto.myapplication.ui.viewmodel.DetalleEstacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEstacionScreen(
    estacionId: String,
    onNavigateBack: () -> Unit,
    viewModel: DetalleEstacionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current
    val favoritosManager = remember { FavoritosManagerSingleton.getInstance(context) }
    
    LaunchedEffect(estacionId) {
        viewModel.loadEstacion(estacionId)
    }
    
    val estacion = uiState.estacion
    val favoritosEstaciones by favoritosManager.favoritosEstaciones.collectAsState()
    val esFavorita = remember(estacion?.id, favoritosEstaciones) {
        estacion?.id?.let { favoritosManager.esEstacionFavorita(it) } ?: false
    }
    
    val servicios = remember(estacionId) {
        ServiciosData.getServiciosPorEstacion(estacionId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = uiState.estacion?.nombre ?: "Cargando..."
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    estacion?.let {
                        IconButton(
                            onClick = {
                                if (esFavorita) {
                                    favoritosManager.eliminarEstacionFavorita(it.id)
                                } else {
                                    favoritosManager.agregarEstacionFavorita(it.id, it.nombre, it.linea)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (esFavorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (esFavorita) "Eliminar de favoritos" else "Agregar a favoritos",
                                tint = if (esFavorita) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error al cargar la estación",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.error!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        } else {
            uiState.estacion?.let { estacion ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Header con información principal
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = when (estacion.linea) {
                                    "Línea 1" -> MaterialTheme.colorScheme.primaryContainer
                                    "Línea 2" -> MaterialTheme.colorScheme.secondaryContainer
                                    else -> MaterialTheme.colorScheme.tertiaryContainer
                                }
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = estacion.nombre,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Surface(
                                    color = when (estacion.linea) {
                                        "Línea 1" -> MaterialTheme.colorScheme.primary
                                        "Línea 2" -> MaterialTheme.colorScheme.secondary
                                        else -> MaterialTheme.colorScheme.tertiary
                                    },
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text(
                                        text = estacion.linea,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = estacion.distrito,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    
                    item {
                        Text(
                            text = "Información General",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
            item {
                InfoCard(
                    icon = Icons.Filled.Info,
                    title = "Horarios",
                    content = "Apertura: ${estacion.horarioApertura}\nCierre: ${estacion.horarioCierre}"
                )
            }
            
            item {
                InfoCard(
                    icon = Icons.Filled.Info,
                    title = "Ubicación",
                    content = "Lat: ${estacion.latitud}\nLng: ${estacion.longitud}"
                )
            }
                    
                    item {
                        Text(
                            text = "Servicios de la Estación",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ServiceCard(
                                icon = Icons.Filled.CheckCircle,
                                title = "Accesible",
                                isAvailable = estacion.tieneAcceso
                            )
                            
                            ServiceCard(
                                icon = Icons.Filled.LocationOn,
                                title = "Estacionamiento",
                                isAvailable = estacion.tieneEstacionamiento
                            )
                            
                            ServiceCard(
                                icon = Icons.Filled.Info,
                                title = "Bicicletero",
                                isAvailable = estacion.tieneBicicletero
                            )
                        }
                    }
                    
                    if (servicios.isNotEmpty()) {
                        item {
                            Text(
                                text = "Servicios Cercanos",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        items(servicios) { servicio ->
                            ServicioCercanoCard(servicio = servicio)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAvailable) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isAvailable) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = if (isAvailable) 
                    MaterialTheme.colorScheme.onPrimaryContainer 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ServicioCercanoCard(servicio: com.proyecto.myapplication.data.model.ServicioEstacion) {
    val icon = when (servicio.tipo) {
        TipoServicio.RESTAURANTE -> Icons.Filled.Info
        TipoServicio.BANCO -> Icons.Filled.Info
        TipoServicio.FARMACIA -> Icons.Filled.Info
        TipoServicio.UNIVERSIDAD -> Icons.Filled.Info
        TipoServicio.HOSPITAL -> Icons.Filled.Info
        TipoServicio.COMERCIO -> Icons.Filled.ShoppingCart
        TipoServicio.HOTEL -> Icons.Filled.Place
        TipoServicio.PARQUE -> Icons.Filled.Place
        TipoServicio.OTRO -> Icons.Filled.Place
    }
    
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
                imageVector = icon,
                contentDescription = servicio.tipo.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = servicio.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                if (servicio.descripcion != null) {
                    Text(
                        text = servicio.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Text(
                text = "${servicio.distancia}m",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
