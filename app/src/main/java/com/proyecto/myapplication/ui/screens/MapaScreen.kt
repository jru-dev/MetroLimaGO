package com.proyecto.myapplication.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.os.Looper
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.util.Locale
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    
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
    
    // Estado de ubicación del usuario
    var ubicacionUsuario by remember { mutableStateOf<LatLng?>(null) }
    var permisosConcedidos by remember { mutableStateOf(false) }
    var estacionSeleccionada by remember { mutableStateOf<EstacionFicticia?>(null) }
    
    // Verificar permisos
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permisosConcedidos = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }
    
    // Verificar permisos al inicio
    LaunchedEffect(Unit) {
        val fineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        
        val coarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        
        permisosConcedidos = fineLocation || coarseLocation
        
        if (!permisosConcedidos) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    
    // Función para validar si una ubicación es razonable (no acepta ubicaciones muy lejanas)
    // NOTA: Esta función acepta cualquier ubicación válida, no filtra por región
    // para que funcione en cualquier lugar del mundo
    fun esUbicacionValida(lat: Double, lon: Double, accuracy: Float = Float.MAX_VALUE): Boolean {
        // Verificar que no sea 0,0 o valores inválidos
        val esValidaGeneral = lat != 0.0 && lon != 0.0 &&
                lat >= -90 && lat <= 90 &&
                lon >= -180 && lon <= 180
        
        // Preferir ubicaciones con buena precisión (menos de 100 metros)
        // Pero aceptar cualquier ubicación válida si tiene precisión razonable
        return esValidaGeneral && (accuracy <= 100.0f || accuracy == Float.MAX_VALUE)
    }
    
    // Obtener ubicación del usuario con LocationCallback para actualizaciones continuas
    DisposableEffect(permisosConcedidos) {
        var locationCallback: LocationCallback? = null
        var fusedLocationClient: FusedLocationProviderClient? = null
        
        if (permisosConcedidos) {
            try {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                
                // Crear LocationRequest con GPS de alta precisión (no usar red)
                val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                    .setMaxUpdateDelayMillis(10000)
                    .setMinUpdateIntervalMillis(2000)
                    .setWaitForAccurateLocation(true) // Esperar ubicación precisa
                    .build()
                
                // Crear LocationCallback para recibir actualizaciones
                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        locationResult.lastLocation?.let { location ->
                            // Verificar que la ubicación sea válida y tenga buena precisión
                            // Solo usar ubicaciones con precisión GPS (no mock)
                            if (esUbicacionValida(location.latitude, location.longitude, location.accuracy)) {
                                // Verificar que la ubicación provenga de GPS (no mock)
                                // isFromMockProvider solo está disponible en API 31+, 
                                // pero podemos confiar en la precisión
                                if (location.accuracy > 0 && location.accuracy < 200.0f) {
                                    // Preferir ubicaciones con mejor precisión
                                    if (ubicacionUsuario == null || location.accuracy < 50.0f) {
                                        ubicacionUsuario = LatLng(location.latitude, location.longitude)
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Solicitar actualizaciones de ubicación
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
                
                // También intentar obtener la ubicación actual inmediatamente
                kotlinx.coroutines.CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val locationResult = fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            null
                        ).await()
                        
                        locationResult?.let { location ->
                            // Verificar que tenga precisión válida (no mock)
                            if (location.accuracy > 0 && location.accuracy < 200.0f &&
                                esUbicacionValida(location.latitude, location.longitude, location.accuracy)) {
                                // Solo usar si tiene buena precisión o si no tenemos ubicación aún
                                if (ubicacionUsuario == null || location.accuracy < 50.0f) {
                                    ubicacionUsuario = LatLng(location.latitude, location.longitude)
                                }
                            } else {
                                // Si la ubicación no es válida, intentar última ubicación conocida
                                val lastLocation = fusedLocationClient.lastLocation.await()
                                lastLocation?.let { loc ->
                                    if (loc.accuracy > 0 && loc.accuracy < 200.0f &&
                                        esUbicacionValida(loc.latitude, loc.longitude, loc.accuracy)) {
                                        if (ubicacionUsuario == null || loc.accuracy < 50.0f) {
                                            ubicacionUsuario = LatLng(loc.latitude, loc.longitude)
                                        }
                                    }
                                }
                            }
                        } ?: run {
                            // Intentar obtener última ubicación conocida
                            try {
                                val lastLocation = fusedLocationClient.lastLocation.await()
                                lastLocation?.let { loc ->
                                    if (loc.accuracy > 0 && loc.accuracy < 200.0f &&
                                        esUbicacionValida(loc.latitude, loc.longitude, loc.accuracy)) {
                                        if (ubicacionUsuario == null || loc.accuracy < 50.0f) {
                                            ubicacionUsuario = LatLng(loc.latitude, loc.longitude)
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                // Si falla, no hacer nada y dejar que el LocationCallback lo actualice
                            }
                        }
                    } catch (e: Exception) {
                        // Si falla, intentar última ubicación conocida
                        try {
                            val lastLocation = fusedLocationClient.lastLocation.await()
                            lastLocation?.let { location ->
                                if (location.accuracy > 0 && location.accuracy < 200.0f &&
                                    esUbicacionValida(location.latitude, location.longitude, location.accuracy)) {
                                    if (ubicacionUsuario == null || location.accuracy < 50.0f) {
                                        ubicacionUsuario = LatLng(location.latitude, location.longitude)
                                    }
                                }
                            }
                        } catch (e2: Exception) {
                            // Si todo falla, no establecer ubicación
                        }
                    }
                }
            } catch (e: Exception) {
                // Si falla completamente, intentar última ubicación conocida
                try {
                    val client = LocationServices.getFusedLocationProviderClient(context)
                    kotlinx.coroutines.CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val lastLocation = client.lastLocation.await()
                            lastLocation?.let { location ->
                                if (location.accuracy > 0 && location.accuracy < 200.0f &&
                                    esUbicacionValida(location.latitude, location.longitude, location.accuracy)) {
                                    if (ubicacionUsuario == null || location.accuracy < 50.0f) {
                                        ubicacionUsuario = LatLng(location.latitude, location.longitude)
                                    }
                                }
                            }
                        } catch (e2: Exception) {
                            // Si todo falla, no establecer ubicación
                        }
                    }
                } catch (e3: Exception) {
                    // Si todo falla, no establecer ubicación
                }
            }
        }
        
        onDispose {
            locationCallback?.let { callback ->
                fusedLocationClient?.removeLocationUpdates(callback)
            }
        }
    }
    
    // Calcular distancias y ordenar estaciones
    val estacionesConDistancia = remember(ubicacionUsuario) {
        ubicacionUsuario?.let { userLocation ->
            estacionesFicticias.map { estacion ->
                val distancia = calcularDistancia(
                    userLocation.latitude,
                    userLocation.longitude,
                    estacion.latitud,
                    estacion.longitud
                )
                EstacionConDistancia(estacion, distancia)
            }.sortedBy { it.distancia }
        } ?: estacionesFicticias.map { EstacionConDistancia(it, null) }
    }
    
    // Posición inicial de la cámara
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            ubicacionUsuario ?: centroLima,
            if (ubicacionUsuario != null) 15f else 12f,
            0f,
            0f
        )
    }
    
    // Actualizar cámara cuando se obtiene la ubicación del usuario
    LaunchedEffect(ubicacionUsuario) {
        ubicacionUsuario?.let { userLocation ->
            // Solo actualizar si es una ubicación real (no centroLima por defecto)
            if (userLocation.latitude != centroLima.latitude || userLocation.longitude != centroLima.longitude) {
                cameraPositionState.position = CameraPosition(userLocation, 15f, 0f, 0f)
            }
        }
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
                actions = {
                    // Botón para establecer ubicación de Lima (útil para emulador)
                    IconButton(onClick = {
                        ubicacionUsuario = centroLima
                        cameraPositionState.position = CameraPosition(centroLima, 15f, 0f, 0f)
                    }) {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = "Usar ubicación de Lima",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    
                    if (!permisosConcedidos) {
                        IconButton(onClick = {
                            launcher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = "Activar ubicación",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        // Botón para centrar en la ubicación del usuario
                        IconButton(onClick = {
                            ubicacionUsuario?.let {
                                cameraPositionState.position = CameraPosition(it, 15f, 0f, 0f)
                            }
                        }) {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = "Mi Ubicación",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
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
                        mapType = MapType.NORMAL,
                        isMyLocationEnabled = permisosConcedidos
                    ),
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = true,
                        compassEnabled = true,
                        myLocationButtonEnabled = permisosConcedidos
                    )
                ) {
                    // Marcador de ubicación del usuario
                    ubicacionUsuario?.let { userLocation ->
                        Marker(
                            state = MarkerState(position = userLocation),
                            title = "Mi Ubicación",
                            snippet = "Tu posición actual",
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        )
                    }
                    
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
                        LeyendaItem(Color(0xFF2196F3), "Mi Ubicación")
                        LeyendaItem(Color(0xFFE30613), "Línea 1")
                        LeyendaItem(Color(0xFFFFD700), "Línea 2")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Lista de estaciones ordenadas por distancia
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
                        text = if (ubicacionUsuario != null) "Estaciones más cercanas" else "Estaciones Cercanas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (!permisosConcedidos) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Warning,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Ubicación no disponible",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Activa la ubicación para ver las estaciones más cercanas",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    estacionesConDistancia.forEach { estacionConDist ->
                        EstacionMapaItem(
                            estacion = estacionConDist.estacion,
                            distancia = estacionConDist.distancia,
                            isSelected = estacionSeleccionada?.id == estacionConDist.estacion.id,
                            onClick = { 
                                estacionSeleccionada = estacionConDist.estacion
                                // Mover la cámara a la estación seleccionada
                                cameraPositionState.position = CameraPosition(
                                    LatLng(estacionConDist.estacion.latitud, estacionConDist.estacion.longitud),
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
        val distancia = ubicacionUsuario?.let { userLocation ->
            calcularDistancia(
                userLocation.latitude,
                userLocation.longitude,
                estacionSeleccionada!!.latitud,
                estacionSeleccionada!!.longitud
            )
        }
        
        AlertDialog(
            onDismissRequest = { estacionSeleccionada = null },
            title = { Text(estacionSeleccionada!!.nombre) },
            text = {
                Column {
                    Text("Línea: ${estacionSeleccionada!!.linea}")
                    Text("Ubicación: ${String.format(Locale.getDefault(), "%.4f", estacionSeleccionada!!.latitud)}, ${String.format(Locale.getDefault(), "%.4f", estacionSeleccionada!!.longitud)}")
                    distancia?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Distancia: ${String.format(Locale.getDefault(), "%.2f", it)} km",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
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

// Función para calcular distancia entre dos puntos (Haversine)
private fun calcularDistancia(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Double {
    val r = 6371.0 // Radio de la Tierra en kilómetros
    val dLat = (lat2 - lat1) * PI / 180
    val dLon = (lon2 - lon1) * PI / 180
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1 * PI / 180) * cos(lat2 * PI / 180) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return r * c
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
    distancia: Double?,
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = estacion.linea,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    distancia?.let {
                        Text(
                            text = "• ${String.format(Locale.getDefault(), "%.2f", it)} km",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
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

data class EstacionConDistancia(
    val estacion: EstacionFicticia,
    val distancia: Double?
)
