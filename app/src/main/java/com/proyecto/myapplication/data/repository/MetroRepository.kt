package com.proyecto.myapplication.data.repository

import com.proyecto.myapplication.data.local.EstacionDao
import com.proyecto.myapplication.data.local.RutaDao
import com.proyecto.myapplication.data.local.ConfiguracionDao
import com.proyecto.myapplication.data.local.AlertaDao
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.data.model.Ruta
import com.proyecto.myapplication.data.model.Configuracion
import com.proyecto.myapplication.data.model.Alerta
import com.proyecto.myapplication.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetroRepository @Inject constructor(
    private val estacionDao: EstacionDao,
    private val rutaDao: RutaDao,
    private val configuracionDao: ConfiguracionDao,
    private val alertaDao: AlertaDao,
    private val apiService: ApiService
) {
    // Estaciones
    fun getAllEstaciones(): Flow<List<Estacion>> = estacionDao.getAllEstaciones()
    
    suspend fun getEstacionById(id: String): Estacion? = estacionDao.getEstacionById(id)
    
    fun getEstacionesByLinea(linea: String): Flow<List<Estacion>> = estacionDao.getEstacionesByLinea(linea)
    
    fun searchEstaciones(query: String): Flow<List<Estacion>> = estacionDao.searchEstaciones(query)
    
    suspend fun insertEstacion(estacion: Estacion) = estacionDao.insertEstacion(estacion)
    
    suspend fun insertEstaciones(estaciones: List<Estacion>) = estacionDao.insertEstaciones(estaciones)
    
    // Rutas
    fun getAllRutas(): Flow<List<Ruta>> = rutaDao.getAllRutas()
    
    fun getRutasFavoritas(): Flow<List<Ruta>> = rutaDao.getRutasFavoritas()
    
    suspend fun insertRuta(ruta: Ruta) = rutaDao.insertRuta(ruta)
    
    suspend fun updateRuta(ruta: Ruta) = rutaDao.updateRuta(ruta)
    
    suspend fun deleteRuta(ruta: Ruta) = rutaDao.deleteRuta(ruta)
    
    // Configuración
    fun getConfiguracion(): Flow<Configuracion?> = configuracionDao.getConfiguracion()
    
    suspend fun updateConfiguracion(configuracion: Configuracion) = configuracionDao.updateConfiguracion(configuracion)
    
    suspend fun updateModoOscuro(modoOscuro: Boolean) = configuracionDao.updateModoOscuro(modoOscuro)
    
    suspend fun updateIdioma(idioma: String) = configuracionDao.updateIdioma(idioma)
    
    // Alertas
    fun getAllAlertas(): Flow<List<Alerta>> = alertaDao.getAllAlertas()
    
    suspend fun getAlertaById(id: String): Alerta? = alertaDao.getAlertaById(id)
    
    fun getAlertasByTipo(tipo: String): Flow<List<Alerta>> = alertaDao.getAlertasByTipo(tipo)
    
    fun getAlertasActivas(): Flow<List<Alerta>> = alertaDao.getAlertasActivas(System.currentTimeMillis())
    
    suspend fun insertAlerta(alerta: Alerta) = alertaDao.insertAlerta(alerta)
    
    suspend fun insertAlertas(alertas: List<Alerta>) = alertaDao.insertAlertas(alertas)
    
    suspend fun updateAlerta(alerta: Alerta) = alertaDao.updateAlerta(alerta)
    
    suspend fun deleteAlerta(alerta: Alerta) = alertaDao.deleteAlerta(alerta)
    
    // Datos externos (API)
    suspend fun getDatosExternos() = apiService.getDatosExternos()
    
    suspend fun getEstacionesRemotas() = apiService.getEstacionesRemotas()
}
