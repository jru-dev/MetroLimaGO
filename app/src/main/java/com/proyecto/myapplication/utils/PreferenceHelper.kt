package com.proyecto.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceHelper(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "MetroLimaGo_Preferences"
        private const val KEY_MODO_OSCURO = "modo_oscuro"
        private const val KEY_IDIOMA = "idioma"
        private const val KEY_NOTIFICACIONES = "notificaciones"
        private const val KEY_TEMA = "tema"
        private const val KEY_VERSION = "version"
        private const val KEY_ULTIMA_SINCRONIZACION = "ultima_sincronizacion"
        
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
    
    // SharedPreferences (método tradicional)
    fun setModoOscuro(activo: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_MODO_OSCURO, activo).apply()
    }
    
    fun getModoOscuro(): Boolean {
        return sharedPreferences.getBoolean(KEY_MODO_OSCURO, false)
    }
    
    fun setIdioma(idioma: String) {
        sharedPreferences.edit().putString(KEY_IDIOMA, idioma).apply()
    }
    
    fun getIdioma(): String {
        return sharedPreferences.getString(KEY_IDIOMA, "es") ?: "es"
    }
    
    fun setNotificaciones(activo: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICACIONES, activo).apply()
    }
    
    fun getNotificaciones(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICACIONES, true)
    }
    
    fun setTema(tema: String) {
        sharedPreferences.edit().putString(KEY_TEMA, tema).apply()
    }
    
    fun getTema(): String {
        return sharedPreferences.getString(KEY_TEMA, "default") ?: "default"
    }
    
    fun setVersion(version: String) {
        sharedPreferences.edit().putString(KEY_VERSION, version).apply()
    }
    
    fun getVersion(): String {
        return sharedPreferences.getString(KEY_VERSION, "1.0") ?: "1.0"
    }
    
    fun setUltimaSincronizacion(timestamp: Long) {
        sharedPreferences.edit().putLong(KEY_ULTIMA_SINCRONIZACION, timestamp).apply()
    }
    
    fun getUltimaSincronizacion(): Long {
        return sharedPreferences.getLong(KEY_ULTIMA_SINCRONIZACION, 0L)
    }
    
    // DataStore (método moderno)
    suspend fun saveModoOscuro(context: Context, activo: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_MODO_OSCURO)] = activo
        }
    }
    
    fun getModoOscuroFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(KEY_MODO_OSCURO)] ?: false
        }
    }
    
    suspend fun saveIdioma(context: Context, idioma: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(KEY_IDIOMA)] = idioma
        }
    }
    
    fun getIdiomaFlow(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(KEY_IDIOMA)] ?: "es"
        }
    }
}