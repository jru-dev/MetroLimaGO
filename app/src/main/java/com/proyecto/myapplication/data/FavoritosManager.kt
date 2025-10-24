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

class FavoritosManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("favoritos", Context.MODE_PRIVATE)
    
    private val _favoritos = MutableStateFlow<List<RutaFavorita>>(emptyList())
    val favoritos: StateFlow<List<RutaFavorita>> = _favoritos.asStateFlow()
    
    init {
        cargarFavoritos()
    }
    
    private fun cargarFavoritos() {
        val favoritosJson = prefs.getStringSet("rutas_favoritas", emptySet()) ?: emptySet()
        val lista = favoritosJson.mapNotNull { json ->
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
        _favoritos.value = lista
    }
    
    fun agregarFavorito(origen: String, destino: String) {
        val id = "${origen}_${destino}"
        val nuevoFavorito = RutaFavorita(id, origen, destino)
        
        val favoritosActuales = _favoritos.value.toMutableList()
        // Eliminar si ya existe para evitar duplicados
        favoritosActuales.removeAll { it.id == id }
        favoritosActuales.add(0, nuevoFavorito)
        
        // Limitar a 10 favoritos
        if (favoritosActuales.size > 10) {
            favoritosActuales.removeAt(favoritosActuales.size - 1)
        }
        
        _favoritos.value = favoritosActuales
        guardarFavoritos()
    }
    
    fun eliminarFavorito(id: String) {
        val favoritosActuales = _favoritos.value.toMutableList()
        favoritosActuales.removeAll { it.id == id }
        _favoritos.value = favoritosActuales
        guardarFavoritos()
    }
    
    fun esFavorito(origen: String, destino: String): Boolean {
        val id = "${origen}_${destino}"
        return _favoritos.value.any { it.id == id }
    }
    
    private fun guardarFavoritos() {
        val favoritosJson = _favoritos.value.map { 
            "${it.id}|${it.origen}|${it.destino}|${it.timestamp}"
        }.toSet()
        prefs.edit().putStringSet("rutas_favoritas", favoritosJson).apply()
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

