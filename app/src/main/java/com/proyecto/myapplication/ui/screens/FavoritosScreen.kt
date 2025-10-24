package com.proyecto.myapplication.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.myapplication.data.FavoritosManagerSingleton
import com.proyecto.myapplication.data.RutaFavorita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPlanificador: (String, String) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val favoritosManager = remember { FavoritosManagerSingleton.getInstance(context) }
    val favoritos by favoritosManager.favoritos.collectAsState()
    
    var mostrarDialogoEliminar by remember { mutableStateOf<RutaFavorita?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Rutas Favoritas",
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
        if (favoritos.isEmpty()) {
            // Pantalla vacía
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tienes rutas favoritas",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Agrega rutas desde el planificador",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritos) { favorito ->
                    FavoritoCard(
                        favorito = favorito,
                        onPlanificar = { origen, destino ->
                            onNavigateToPlanificador(origen, destino)
                        },
                        onEliminar = { favorito ->
                            mostrarDialogoEliminar = favorito
                        }
                    )
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
private fun FavoritoCard(
    favorito: RutaFavorita,
    onPlanificar: (String, String) -> Unit,
    onEliminar: (RutaFavorita) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de favorito
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color(0xFFE30613),
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Información de la ruta
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
                    text = "Agregado el ${formatearFecha(favorito.timestamp)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón para planificar
                IconButton(
                    onClick = { onPlanificar(favorito.origen, favorito.destino) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Planificar ruta",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Botón para eliminar
                IconButton(
                    onClick = { onEliminar(favorito) },
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
    val formato = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return formato.format(fecha)
}
