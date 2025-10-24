package com.proyecto.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.myapplication.data.model.Estacion

@Composable
fun EstacionSelector(
    label: String,
    estacion: Estacion?,
    onEstacionSelected: (Estacion) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (estacion != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
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
                    
                    TextButton(
                        onClick = onSearchClicked
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Buscar estación"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cambiar")
                    }
                }
            } else {
                OutlinedButton(
                    onClick = onSearchClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar estación"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Seleccionar estación")
                }
            }
        }
    }
}
