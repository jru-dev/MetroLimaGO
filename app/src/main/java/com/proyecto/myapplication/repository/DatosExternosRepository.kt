package com.proyecto.myapplication.repository

import com.proyecto.myapplication.data.model.DatosExternos
import com.proyecto.myapplication.data.network.RetrofitClient
import com.proyecto.myapplication.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class DatosExternosRepository(
    private val preferenceHelper: PreferenceHelper
) {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun obtenerHorarios(): Result<DatosExternos> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerHorarios()
            if (response.isSuccessful) {
                val datos = response.body()
                if (datos != null) {
                    // Guardar timestamp de última sincronización
                    preferenceHelper.setUltimaSincronizacion(System.currentTimeMillis())
                    Result.success(datos)
                } else {
                    Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                Result.failure(Exception("Error del servidor: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error inesperado: ${e.message}"))
        }
    }
    
    suspend fun obtenerConfiguracion(): Result<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerConfiguracion()
            if (response.isSuccessful) {
                val config = response.body()
                if (config != null) {
                    Result.success(config)
                } else {
                    Result.failure(Exception("Configuración vacía del servidor"))
                }
            } else {
                Result.failure(Exception("Error del servidor: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error inesperado: ${e.message}"))
        }
    }
    
    fun obtenerUltimaSincronizacion(): Long {
        return preferenceHelper.getUltimaSincronizacion()
    }
    
    fun obtenerVersion(): String {
        return preferenceHelper.getVersion()
    }
}