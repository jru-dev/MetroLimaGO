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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(
    onNavigateBack: () -> Unit
) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var locationEnabled by remember { mutableStateOf(true) }
    var autoUpdate by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Configuración",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Preferencias",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Personaliza tu experiencia",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Sección de Apariencia
            item {
                ConfigSection(
                    title = "Apariencia",
                    icon = Icons.Filled.Settings
                ) {
                    ConfigItem(
                        title = "Modo Oscuro",
                        subtitle = "Cambiar entre tema claro y oscuro",
                        icon = Icons.Filled.Info,
                        trailing = {
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { isDarkTheme = it }
                            )
                        }
                    )
                }
            }

            // Sección de Notificaciones
            item {
                ConfigSection(
                    title = "Notificaciones",
                    icon = Icons.Filled.Notifications
                ) {
                    ConfigItem(
                        title = "Alertas del Metro",
                        subtitle = "Recibir notificaciones sobre el servicio",
                        icon = Icons.Filled.Notifications,
                        trailing = {
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it }
                            )
                        }
                    )
                }
            }

            // Sección de Ubicación
            item {
                ConfigSection(
                    title = "Ubicación",
                    icon = Icons.Filled.LocationOn
                ) {
                    ConfigItem(
                        title = "Ubicación Automática",
                        subtitle = "Detectar estación más cercana",
                        icon = Icons.Filled.LocationOn,
                        trailing = {
                            Switch(
                                checked = locationEnabled,
                                onCheckedChange = { locationEnabled = it }
                            )
                        }
                    )
                }
            }

            // Sección de Datos
            item {
                ConfigSection(
                    title = "Datos",
                    icon = Icons.Filled.Info
                ) {
                    ConfigItem(
                        title = "Actualización Automática",
                        subtitle = "Descargar datos del Metro automáticamente",
                        icon = Icons.Filled.Refresh,
                        trailing = {
                            Switch(
                                checked = autoUpdate,
                                onCheckedChange = { autoUpdate = it }
                            )
                        }
                    )
                    ConfigItem(
                        title = "Limpiar Cache",
                        subtitle = "Liberar espacio de almacenamiento",
                        icon = Icons.Filled.Delete,
                        onClick = { /* Limpiar cache */ }
                    )
                }
            }

            // Sección de Información
            item {
                ConfigSection(
                    title = "Información",
                    icon = Icons.Filled.Info
                ) {
                    ConfigItem(
                        title = "Versión de la App",
                        subtitle = "1.0.0",
                        icon = Icons.Filled.Info,
                        onClick = { /* Mostrar info de versión */ }
                    )
                    ConfigItem(
                        title = "Términos y Condiciones",
                        subtitle = "Leer términos de uso",
                        icon = Icons.Filled.Info,
                        onClick = { /* Mostrar términos */ }
                    )
                    ConfigItem(
                        title = "Política de Privacidad",
                        subtitle = "Cómo usamos tus datos",
                        icon = Icons.Filled.Info,
                        onClick = { /* Mostrar política */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun ConfigSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun ConfigItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable { onClick() } else it },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
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
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            trailing?.invoke()
        }
    }
}