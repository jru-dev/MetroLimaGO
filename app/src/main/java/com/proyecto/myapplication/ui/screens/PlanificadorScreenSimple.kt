package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.myapplication.data.EstacionesData
import com.proyecto.myapplication.data.Estacion
import com.proyecto.myapplication.data.FavoritosManagerSingleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorScreenSimple(
    onNavigateBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val favoritosManager = remember { FavoritosManagerSingleton.getInstance(context) }
    val favoritos by favoritosManager.favoritos.collectAsState()
    
    var estacionOrigen by remember { mutableStateOf<Estacion?>(null) }
    var estacionDestino by remember { mutableStateOf<Estacion?>(null) }
    var mostrarEstaciones by remember { mutableStateOf(false) }
    var seleccionandoOrigen by remember { mutableStateOf(true) }
    var query by remember { mutableStateOf("") }
    var rutaCalculada by remember { mutableStateOf<List<Estacion>?>(null) }
    var mostrarMensajeFavorito by remember { mutableStateOf(false) }
    
    val estacionesFiltradas = remember(query) {
        EstacionesData.buscarEstaciones(query)
    }
    
    fun calcularRuta() {
        if (estacionOrigen != null && estacionDestino != null) {
            val origen = estacionOrigen!!
            val destino = estacionDestino!!
            
            // Si están en la misma línea, calcular ruta directa
            if (origen.linea == destino.linea) {
                val estacionesLinea = EstacionesData.getEstacionesPorLinea(origen.linea)
                val indiceOrigen = estacionesLinea.indexOfFirst { it.id == origen.id }
                val indiceDestino = estacionesLinea.indexOfFirst { it.id == destino.id }
                
                if (indiceOrigen != -1 && indiceDestino != -1) {
                    val inicio = minOf(indiceOrigen, indiceDestino)
                    val fin = maxOf(indiceOrigen, indiceDestino)
                    rutaCalculada = estacionesLinea.subList(inicio, fin + 1)
                }
            } else {
                // Para diferentes líneas, mostrar mensaje de conexión
                rutaCalculada = listOf(origen, destino)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificar Ruta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE30613)
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
                Text(
                    text = "Selecciona tu ruta",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                // Selector de estación origen
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Estación de Origen",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        if (estacionOrigen != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = estacionOrigen!!.nombre,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "${estacionOrigen!!.linea} - ${estacionOrigen!!.distrito}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                TextButton(
                                    onClick = { 
                                        mostrarEstaciones = true
                                        seleccionandoOrigen = true
                                        query = ""
                                    }
                                ) {
                                    Text("Cambiar")
                                }
                            }
                        } else {
                            OutlinedButton(
                                onClick = { 
                                    mostrarEstaciones = true
                                    seleccionandoOrigen = true
                                    query = ""
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Filled.Search, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Seleccionar estación")
                            }
                        }
                    }
                }
            }
            
            item {
                // Botón para intercambiar estaciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { 
                            val temp = estacionOrigen
                            estacionOrigen = estacionDestino
                            estacionDestino = temp
                            rutaCalculada = null
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowForward,
                            contentDescription = "Intercambiar estaciones"
                        )
                    }
                }
            }
            
            item {
                // Selector de estación destino
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Estación de Destino",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        if (estacionDestino != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = estacionDestino!!.nombre,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "${estacionDestino!!.linea} - ${estacionDestino!!.distrito}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                TextButton(
                                    onClick = { 
                                        mostrarEstaciones = true
                                        seleccionandoOrigen = false
                                        query = ""
                                    }
                                ) {
                                    Text("Cambiar")
                                }
                            }
                        } else {
                            OutlinedButton(
                                onClick = { 
                                    mostrarEstaciones = true
                                    seleccionandoOrigen = false
                                    query = ""
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Filled.Search, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Seleccionar estación")
                            }
                        }
                    }
                }
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { calcularRuta() },
                        enabled = estacionOrigen != null && estacionDestino != null,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "Calcular ruta")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Calcular Ruta")
                    }
                    
                    // Botón de favoritos
                    if (estacionOrigen != null && estacionDestino != null) {
                        val esFavorito = favoritosManager.esFavorito(
                            estacionOrigen!!.nombre,
                            estacionDestino!!.nombre
                        )
                        IconButton(
                            onClick = {
                                if (esFavorito) {
                                    favoritosManager.eliminarFavorito("${estacionOrigen!!.nombre}_${estacionDestino!!.nombre}")
                                } else {
                                    favoritosManager.agregarFavorito(
                                        estacionOrigen!!.nombre,
                                        estacionDestino!!.nombre
                                    )
                                    mostrarMensajeFavorito = true
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (esFavorito) "Eliminar de favoritos" else "Agregar a favoritos",
                                tint = if (esFavorito) Color(0xFFE30613) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            
            // Snackbar para mostrar mensaje de favorito agregado
            if (mostrarMensajeFavorito) {
                item {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        mostrarMensajeFavorito = false
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            text = "✓ Ruta agregada a favoritos",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            // Mostrar ruta calculada
            if (rutaCalculada != null) {
                item {
                    Text(
                        text = "Ruta Encontrada",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Información de la Ruta",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = null,
                                    tint = Color(0xFFE30613)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            if (estacionOrigen!!.linea == estacionDestino!!.linea) {
                                val tiempoEstimado = rutaCalculada!!.size * 2 // 2 minutos por estación
                                val estacionesIntermedias = rutaCalculada!!.size - 2
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Filled.Info,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("${tiempoEstimado} min")
                                    }
                                    Text("${estacionesIntermedias} estaciones intermedias")
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Línea: ${estacionOrigen!!.linea}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFE30613)
                                )
                            } else {
                                Text(
                                    text = "Las estaciones están en líneas diferentes. Se requiere transbordo.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                
                item {
                    Text(
                        text = "Estaciones de la Ruta",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(rutaCalculada!!) { estacion ->
                    RutaEstacionCard(estacion = estacion)
                }
            }
        }
    }
    
    // Modal para seleccionar estaciones
    if (mostrarEstaciones) {
        AlertDialog(
            onDismissRequest = { mostrarEstaciones = false },
            title = { 
                Text(
                    if (seleccionandoOrigen) "Seleccionar Origen" else "Seleccionar Destino"
                ) 
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Buscar estación...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier.height(300.dp)
                    ) {
                        items(estacionesFiltradas) { estacion ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                onClick = {
                                    if (seleccionandoOrigen) {
                                        estacionOrigen = estacion
                                    } else {
                                        estacionDestino = estacion
                                    }
                                    mostrarEstaciones = false
                                    rutaCalculada = null
                                }
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = estacion.nombre,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "${estacion.linea} - ${estacion.distrito}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarEstaciones = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun RutaEstacionCard(estacion: Estacion) {
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
                    text = estacion.distrito,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                Icons.Filled.Info,
                contentDescription = "Estación",
                tint = Color(0xFFE30613)
            )
        }
    }
}
