package com.proyecto.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RutaFavorita(
    val id: String,
    val origen: String,
    val destino: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class EstacionFavorita(
    val id: String,
    val nombre: String,
    val linea: String,
    val timestamp: Long = System.currentTimeMillis()
)

class FavoritosManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("favoritos", Context.MODE_PRIVATE)
    
    private val _favoritosRutas = MutableStateFlow<List<RutaFavorita>>(emptyList())
    val favoritosRutas: StateFlow<List<RutaFavorita>> = _favoritosRutas.asStateFlow()
    
    private val _favoritosEstaciones = MutableStateFlow<List<EstacionFavorita>>(emptyList())
    val favoritosEstaciones: StateFlow<List<EstacionFavorita>> = _favoritosEstaciones.asStateFlow()
    
    // Compatibilidad hacia atrás
    val favoritos: StateFlow<List<RutaFavorita>> = _favoritosRutas.asStateFlow()
    
    init {
        cargarFavoritos()
    }
    
    private fun cargarFavoritos() {
        // Cargar rutas favoritas
        val favoritosJson = prefs.getStringSet("rutas_favoritas", emptySet()) ?: emptySet()
        val listaRutas = favoritosJson.mapNotNull { json ->
            try {
                val parts = json.split("|")
                if (parts.size >= 3) {
                    RutaFavorita(
                        id = parts[0],
                        origen = parts[1],
                        destino = parts[2],
                        timestamp = parts.getOrNull(3)?.toLongOrNull() ?: System.currentTimeMillis()
                    )
                } else null
            } catch (e: Exception) {
                null
            }
        }.sortedByDescending { it.timestamp }
        _favoritosRutas.value = listaRutas
        
        // Cargar estaciones favoritas
        val estacionesJson = prefs.getStringSet("estaciones_favoritas", emptySet()) ?: emptySet()
        val listaEstaciones = estacionesJson.mapNotNull { json ->
            try {
                val parts = json.split("|")
                if (parts.size >= 3) {
                    EstacionFavorita(
                        id = parts[0],
                        nombre = parts[1],
                        linea = parts[2],
                        timestamp = parts.getOrNull(3)?.toLongOrNull() ?: System.currentTimeMillis()
                    )
                } else null
            } catch (e: Exception) {
                null
            }
        }.sortedByDescending { it.timestamp }
        _favoritosEstaciones.value = listaEstaciones
    }
    
    // Funciones para rutas favoritas
    fun agregarFavorito(origen: String, destino: String) {
        val id = "${origen}_${destino}"
        val nuevoFavorito = RutaFavorita(id, origen, destino)
        
        val favoritosActuales = _favoritosRutas.value.toMutableList()
        favoritosActuales.removeAll { it.id == id }
        favoritosActuales.add(0, nuevoFavorito)
        
        if (favoritosActuales.size > 10) {
            favoritosActuales.removeAt(favoritosActuales.size - 1)
        }
        
        _favoritosRutas.value = favoritosActuales
        guardarFavoritosRutas()
    }
    
    fun eliminarFavorito(id: String) {
        val favoritosActuales = _favoritosRutas.value.toMutableList()
        favoritosActuales.removeAll { it.id == id }
        _favoritosRutas.value = favoritosActuales
        guardarFavoritosRutas()
    }
    
    fun esFavorito(origen: String, destino: String): Boolean {
        val id = "${origen}_${destino}"
        return _favoritosRutas.value.any { it.id == id }
    }
    
    // Funciones para estaciones favoritas
    fun agregarEstacionFavorita(estacionId: String, nombre: String, linea: String) {
        val nuevoFavorito = EstacionFavorita(estacionId, nombre, linea)
        
        val favoritosActuales = _favoritosEstaciones.value.toMutableList()
        favoritosActuales.removeAll { it.id == estacionId }
        favoritosActuales.add(0, nuevoFavorito)
        
        if (favoritosActuales.size > 20) {
            favoritosActuales.removeAt(favoritosActuales.size - 1)
        }
        
        _favoritosEstaciones.value = favoritosActuales
        guardarFavoritosEstaciones()
    }
    
    fun eliminarEstacionFavorita(estacionId: String) {
        val favoritosActuales = _favoritosEstaciones.value.toMutableList()
        favoritosActuales.removeAll { it.id == estacionId }
        _favoritosEstaciones.value = favoritosActuales
        guardarFavoritosEstaciones()
    }
    
    fun esEstacionFavorita(estacionId: String): Boolean {
        return _favoritosEstaciones.value.any { it.id == estacionId }
    }
    
    private fun guardarFavoritosRutas() {
        val favoritosJson = _favoritosRutas.value.map { 
            "${it.id}|${it.origen}|${it.destino}|${it.timestamp}"
        }.toSet()
        prefs.edit().putStringSet("rutas_favoritas", favoritosJson).apply()
    }
    
    private fun guardarFavoritosEstaciones() {
        val estacionesJson = _favoritosEstaciones.value.map { 
            "${it.id}|${it.nombre}|${it.linea}|${it.timestamp}"
        }.toSet()
        prefs.edit().putStringSet("estaciones_favoritas", estacionesJson).apply()
    }
}

// Singleton para acceso global
object FavoritosManagerSingleton {
    private var instance: FavoritosManager? = null
    
    fun getInstance(context: Context): FavoritosManager {
        return instance ?: synchronized(this) {
            instance ?: FavoritosManager(context.applicationContext).also { instance = it }
        }
    }
}

