package com.proyecto.myapplication.data.local

import com.proyecto.myapplication.data.model.Configuracion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val estacionDao: EstacionDao,
    private val rutaDao: RutaDao,
    private val configuracionDao: ConfiguracionDao,
    private val alertaDao: AlertaDao
) {
    
    suspend fun initializeDatabase() = withContext(Dispatchers.IO) {
        // Insertar estaciones mock
        estacionDao.insertEstaciones(MockData.estacionesMock)
        
        // Insertar rutas mock
        rutaDao.insertRutas(MockData.rutasMock)
        
        // Insertar alertas mock
        alertaDao.insertAlertas(MockData.alertasMock)
        
        // Insertar configuración por defecto
        val configuracionDefault = Configuracion(
            id = 1,
            modoOscuro = false,
            idioma = "es",
            notificaciones = true,
            tema = "sistema"
        )
        configuracionDao.insertConfiguracion(configuracionDefault)
    }
}
