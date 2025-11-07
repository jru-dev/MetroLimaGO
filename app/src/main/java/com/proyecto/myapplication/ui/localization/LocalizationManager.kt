package com.proyecto.myapplication.ui.localization

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LocalizationManager {
    private const val PREFS_NAME = "metro_lima_prefs"
    private const val KEY_LANGUAGE = "language"
    
    private var _currentLanguage by mutableStateOf("es")
    val currentLanguage: String get() = _currentLanguage
    
    private val _languageFlow = MutableStateFlow("es")
    val languageFlow: StateFlow<String> = _languageFlow.asStateFlow()
    
    fun initialize(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedLanguage = prefs.getString(KEY_LANGUAGE, "es") ?: "es"
        _currentLanguage = savedLanguage
        _languageFlow.value = savedLanguage
    }
    
    fun setLanguage(context: Context, language: String) {
        _currentLanguage = language
        _languageFlow.value = language
        
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, language).apply()
    }
}

object LocalizedStrings {
    private val strings = mapOf(
        "es" to mapOf(
            "app_name" to "MetroLima GO",
            "home" to "Inicio",
            "stations" to "Estaciones", 
            "routes" to "Rutas",
            "favorites" to "Favoritos",
            "configuration" to "Configuración",
            "dark_mode" to "Modo Oscuro",
            "language" to "Idioma de la Aplicación",
            "select_language" to "Seleccionar Idioma",
            "spanish" to "Español",
            "english" to "English",
            "close" to "Cerrar",
            "back" to "Volver",
            "search" to "Buscar",
            "from" to "Desde",
            "to" to "Hasta",
            "plan_route" to "Planificar Ruta",
            "add_to_favorites" to "Agregar a Favoritos",
            "remove_from_favorites" to "Eliminar de Favoritos",
            "route_planning" to "Planificación de Rutas",
            "select_origin_station" to "Selecciona la estación de origen",
            "select_destination_station" to "Selecciona la estación de destino",
            "stations_list" to "Lista de Estaciones",
            "line" to "Línea",
            "district" to "Distrito",
            "no_route_found" to "No se encontró ruta",
            "stations_different_lines" to "Las estaciones están en líneas diferentes. Se requiere transbordo.",
            "route_found" to "Ruta encontrada",
            "duration" to "Duración",
            "minutes" to "minutos"
        ),
        "en" to mapOf(
            "app_name" to "MetroLima GO",
            "home" to "Home",
            "stations" to "Stations",
            "routes" to "Routes", 
            "favorites" to "Favorites",
            "configuration" to "Configuration",
            "dark_mode" to "Dark Mode",
            "language" to "App Language",
            "select_language" to "Select Language",
            "spanish" to "Español",
            "english" to "English",
            "close" to "Close",
            "back" to "Back",
            "search" to "Search",
            "from" to "From",
            "to" to "To",
            "plan_route" to "Plan Route",
            "add_to_favorites" to "Add to Favorites",
            "remove_from_favorites" to "Remove from Favorites",
            "route_planning" to "Route Planning",
            "select_origin_station" to "Select origin station",
            "select_destination_station" to "Select destination station",
            "stations_list" to "Stations List",
            "line" to "Line",
            "district" to "District",
            "no_route_found" to "No route found",
            "stations_different_lines" to "Stations are on different lines. Transfer required.",
            "route_found" to "Route found",
            "duration" to "Duration",
            "minutes" to "minutes"
        )
    )
    
    fun getString(key: String): String {
        val language = LocalizationManager.currentLanguage
        return strings[language]?.get(key) ?: strings["es"]?.get(key) ?: key
    }
}
