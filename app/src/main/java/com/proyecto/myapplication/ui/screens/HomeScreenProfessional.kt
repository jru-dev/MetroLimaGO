package com.proyecto.myapplication.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.myapplication.data.EstacionesData
import com.proyecto.myapplication.data.FavoritosManagerSingleton
import com.proyecto.myapplication.data.RutaFavorita
import com.proyecto.myapplication.data.model.Estacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenProfessional(
    onNavigateToEstaciones: () -> Unit,
    onNavigateToPlanificador: () -> Unit,
    onNavigateToFavoritos: () -> Unit = {},
    onNavigateToInfo: () -> Unit = {},
    onNavigateToConfiguracion: () -> Unit = {},
    onNavigateToSeguridad: () -> Unit = {},
    onNavigateToTarifas: () -> Unit = {},
    onNavigateToMapa: () -> Unit = {}
) {
    var estacionOrigen by remember { mutableStateOf<Estacion?>(null) }
    var estacionDestino by remember { mutableStateOf<Estacion?>(null) }
    var mostrarEstaciones by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    
    val estacionesFiltradas = remember(query) {
        EstacionesData.buscarEstaciones(query)
    }
    
    val tabs = listOf("Inicio", "Favoritos")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "MetroLima GO",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { /* Notificaciones */ }) {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = "Notificaciones",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
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
                // Header con gradiente animado
                AnimatedHeader()
            }

            item {
                // Tabs de navegación
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { 
                                Text(
                                    title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                ) 
                            }
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    // Contenido principal
                    item { InfoLinksSection(onNavigateToSeguridad, onNavigateToTarifas, onNavigateToInfo, onNavigateToMapa) }
                    item { ServiceInfoSection() }
                }
                1 -> {
                    // Favoritos
                    item { 
                        FavoritesSection(
                            onNavigateToPlanificador = onNavigateToPlanificador
                        ) 
                    }
                }
            }

            item {
                // Información del servicio
                ServiceStatusCard()
            }
        }
    }
}

@Composable
private fun AnimatedHeader() {
    val infiniteTransition = rememberInfiniteTransition(label = "header")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🚇",
                    fontSize = 48.sp,
                    modifier = Modifier.alpha(alpha)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Bienvenido al Metro de Lima",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Planifica tu viaje de forma rápida y eficiente",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun InfoLinksSection(
    onNavigateToSeguridad: () -> Unit,
    onNavigateToTarifas: () -> Unit,
    onNavigateToInfo: () -> Unit,
    onNavigateToMapa: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Información",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botones en grid 2x2
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButton(
                        title = "Consejos de Seguridad",
                        icon = Icons.Filled.Info,
                        color = MaterialTheme.colorScheme.primary,
                        onClick = onNavigateToSeguridad,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionButton(
                        title = "Tarifas y Pagos",
                        icon = Icons.Filled.Info,
                        color = MaterialTheme.colorScheme.secondary,
                        onClick = onNavigateToTarifas,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButton(
                        title = "Información del Metro",
                        icon = Icons.Filled.Info,
                        color = MaterialTheme.colorScheme.tertiary,
                        onClick = onNavigateToInfo,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionButton(
                        title = "Mapa",
                        icon = Icons.Filled.LocationOn,
                        color = MaterialTheme.colorScheme.primary,
                        onClick = onNavigateToMapa,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    val isDarkTheme = isSystemInDarkTheme()
    
    Card(
        modifier = modifier
            .scale(scale)
            .clickable { 
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) {
                // En tema oscuro: fondo más visible con el color principal
                color.copy(alpha = 0.4f)
            } else {
                // En tema claro: mantener el estilo original
                color.copy(alpha = 0.1f)
            }
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (isDarkTheme) {
            androidx.compose.foundation.BorderStroke(2.dp, color.copy(alpha = 0.6f))
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EstacionSelectorSection(
    estacionOrigen: Estacion?,
    estacionDestino: Estacion?,
    onNavigateToPlanificador: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Planificar Viaje",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            // Selector de estación origen
            EstacionSelector(
                estacion = estacionOrigen,
                label = "Estación Origen",
                icon = Icons.Filled.LocationOn,
                onClick = { /* Seleccionar origen */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Selector de estación destino
            EstacionSelector(
                estacion = estacionDestino,
                label = "Estación Destino",
                icon = Icons.Filled.LocationOn,
                onClick = { /* Seleccionar destino */ }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón de calcular ruta
            Button(
                onClick = onNavigateToPlanificador,
                modifier = Modifier.fillMaxWidth(),
                enabled = estacionOrigen != null && estacionDestino != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Calcular Ruta",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun EstacionSelector(
    estacion: Estacion?,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = estacion?.nombre ?: "Seleccionar estación",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (estacion != null) 
                        MaterialTheme.colorScheme.onSurface 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ServiceInfoSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Información del Servicio",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            ServiceInfoItem(
                icon = Icons.Filled.Info,
                title = "Horario",
                value = "5:00 AM - 11:00 PM"
            )
            ServiceInfoItem(
                icon = Icons.Filled.Info,
                title = "Frecuencia",
                value = "3-5 minutos"
            )
            ServiceInfoItem(
                icon = Icons.Filled.Info,
                title = "Tarifa",
                value = "S/ 1.50"
            )
        }
    }
}

@Composable
private fun ServiceInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
private fun FavoritesSection(
    onNavigateToPlanificador: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val favoritosManager = remember { FavoritosManagerSingleton.getInstance(context) }
    val favoritos by favoritosManager.favoritosRutas.collectAsState()
    var mostrarDialogoEliminar by remember { mutableStateOf<RutaFavorita?>(null) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Rutas Favoritas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            if (favoritos.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No tienes rutas favoritas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Agrega rutas desde el planificador",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    favoritos.forEach { favorito ->
                        FavoriteRouteCard(
                            favorito = favorito,
                            onPlanificar = {
                                onNavigateToPlanificador()
                            },
                            onEliminar = {
                                mostrarDialogoEliminar = favorito
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Diálogo de confirmación para eliminar
    if (mostrarDialogoEliminar != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = null },
            title = { Text("Eliminar Favorito") },
            text = { 
                Text("¿Estás seguro de que quieres eliminar esta ruta de tus favoritos?") 
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        favoritosManager.eliminarFavorito(mostrarDialogoEliminar!!.id)
                        mostrarDialogoEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarDialogoEliminar = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun FavoriteRouteCard(
    favorito: RutaFavorita,
    onPlanificar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${favorito.origen} → ${favorito.destino}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formatearFecha(favorito.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onPlanificar,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Planificar ruta",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar favorito",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

private fun formatearFecha(timestamp: Long): String {
    val fecha = java.util.Date(timestamp)
    val formato = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
    return formato.format(fecha)
}

@Composable
private fun ServiceStatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Servicio Operativo",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Todas las líneas funcionando normalmente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
