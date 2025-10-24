package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ArrowForward
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
fun HomeScreenSimple(
    onNavigateToEstaciones: () -> Unit,
    onNavigateToPlanificador: () -> Unit
) {
    var estacionOrigen by remember { mutableStateOf<Estacion?>(null) }
    var estacionDestino by remember { mutableStateOf<Estacion?>(null) }
    var mostrarEstaciones by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    
    val estacionesFiltradas = remember(query) {
        EstacionesData.buscarEstaciones(query)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "MetroLima GO",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE30613) // Rojo del Metro
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE30613).copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "¡Bienvenido al Metro de Lima!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Planifica tu viaje de forma rápida y sencilla",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            
            item {
                Text(
                    text = "Planificar Viaje",
                    style = MaterialTheme.typography.titleLarge,
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
                Button(
                    onClick = onNavigateToPlanificador,
                    enabled = estacionOrigen != null && estacionDestino != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.ArrowForward, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calcular Ruta")
                }
            }
            
            item {
                Divider()
            }
            
            item {
                Text(
                    text = "Información del Metro",
                    style = MaterialTheme.typography.titleLarge,
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
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = null,
                                tint = Color(0xFFE30613)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Horario: 5:00 AM - 11:00 PM",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = null,
                                tint = Color(0xFFE30613)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Frecuencia: 3-5 minutos",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Estado: Operativo",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            item {
                Button(
                    onClick = onNavigateToEstaciones,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.List, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver Todas las Estaciones")
                }
            }
        }
    }
    
    // Modal para seleccionar estaciones
    if (mostrarEstaciones) {
        AlertDialog(
            onDismissRequest = { mostrarEstaciones = false },
            title = { Text("Seleccionar Estación") },
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
                                    if (estacionOrigen == null) {
                                        estacionOrigen = estacion
                                    } else {
                                        estacionDestino = estacion
                                    }
                                    mostrarEstaciones = false
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
