package com.proyecto.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.myapplication.data.model.Alerta
import com.proyecto.myapplication.data.model.TipoAlerta
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AlertaCard(
    alerta: Alerta,
    modifier: Modifier = Modifier
) {
    val icon = when (alerta.tipo) {
        TipoAlerta.MANTENIMIENTO -> Icons.Filled.Build
        TipoAlerta.RETRASO -> Icons.Filled.Warning
        TipoAlerta.SUSPENSION -> Icons.Filled.Close
        TipoAlerta.INFORMACION -> Icons.Filled.Info
    }
    
    val containerColor = when (alerta.tipo) {
        TipoAlerta.MANTENIMIENTO -> MaterialTheme.colorScheme.tertiaryContainer
        TipoAlerta.RETRASO -> MaterialTheme.colorScheme.secondaryContainer
        TipoAlerta.SUSPENSION -> MaterialTheme.colorScheme.errorContainer
        TipoAlerta.INFORMACION -> MaterialTheme.colorScheme.primaryContainer
    }
    
    val iconColor = when (alerta.tipo) {
        TipoAlerta.MANTENIMIENTO -> MaterialTheme.colorScheme.tertiary
        TipoAlerta.RETRASO -> MaterialTheme.colorScheme.secondary
        TipoAlerta.SUSPENSION -> MaterialTheme.colorScheme.error
        TipoAlerta.INFORMACION -> MaterialTheme.colorScheme.primary
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = alerta.tipo.name,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = alerta.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = alerta.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Estaciones afectadas: ${alerta.estacionesAfectadas.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = formatDate(alerta.fechaInicio),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
