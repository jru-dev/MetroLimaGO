package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeguridadScreen(
    onNavigateBack: () -> Unit
) {
    val consejos = listOf(
        ConsejoSeguridad(
            "Mantén tu distancia",
            "Mantén una distancia segura del borde del andén mientras esperas el tren.",
            Icons.Filled.Warning
        ),
        ConsejoSeguridad(
            "Cuida tus pertenencias",
            "Mantén siempre tus pertenencias cerca y en un lugar seguro. Evita dejar objetos desatendidos.",
            Icons.Filled.Info
        ),
        ConsejoSeguridad(
            "Respeta las señalizaciones",
            "Sigue todas las señalizaciones y normas de seguridad en las estaciones.",
            Icons.Filled.Info
        ),
        ConsejoSeguridad(
            "Espera tu turno",
            "Permite que los pasajeros desciendan antes de abordar el tren.",
            Icons.Filled.Info
        ),
        ConsejoSeguridad(
            "Usa las escaleras y ascensores correctamente",
            "No corras en las escaleras. Usa el pasamanos. En caso de emergencia, usa las escaleras de emergencia.",
            Icons.Filled.Info
        ),
        ConsejoSeguridad(
            "Reporta situaciones sospechosas",
            "Si observas alguna situación sospechosa o de emergencia, reporta inmediatamente al personal de seguridad.",
            Icons.Filled.Warning
        ),
        ConsejoSeguridad(
            "No uses el teléfono cerca del andén",
            "Evita distraerte con el teléfono mientras estás cerca del borde del andén.",
            Icons.Filled.Phone
        ),
        ConsejoSeguridad(
            "Cuida a menores de edad",
            "Si viajas con menores de edad, mantenlos siempre cerca y bajo supervisión.",
            Icons.Filled.Info
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consejos de Seguridad") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Seguridad",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Tu seguridad es nuestra prioridad",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sigue estos consejos para un viaje seguro",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            items(consejos) { consejo ->
                ConsejoCard(consejo = consejo)
            }
        }
    }
}

@Composable
fun ConsejoCard(consejo: ConsejoSeguridad) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = consejo.icon,
                contentDescription = consejo.titulo,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = consejo.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = consejo.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class ConsejoSeguridad(
    val titulo: String,
    val descripcion: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

