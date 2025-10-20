package com.proyecto.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.proyecto.myapplication.data.dao.EstacionDAO
import com.proyecto.myapplication.data.model.Estacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Estacion::class],
    version = 1,
    exportSchema = false
)
abstract class MetroLimaDatabase : RoomDatabase() {

    abstract fun estacionDao(): EstacionDAO

    companion object {
        @Volatile
        private var INSTANCE: MetroLimaDatabase? = null

        fun getDatabase(context: Context): MetroLimaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroLimaDatabase::class.java,
                    "metro_lima_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        poblarBaseDatos(database.estacionDao())
                    }
                }
            }
        }

        private suspend fun poblarBaseDatos(estacionDao: EstacionDAO) {
            val estaciones = listOf(
                Estacion(nombre = "Villa El Salvador", linea = "Línea 1", distrito = "Villa El Salvador", latitud = -12.2124, longitud = -76.9422, orden = 1),
                Estacion(nombre = "Parque Industrial", linea = "Línea 1", distrito = "Villa El Salvador", latitud = -12.1876, longitud = -76.9384, orden = 2),
                Estacion(nombre = "Villa María", linea = "Línea 1", distrito = "Villa María del Triunfo", latitud = -12.1678, longitud = -76.9456, orden = 3),
                Estacion(nombre = "Pumacahua", linea = "Línea 1", distrito = "Villa María del Triunfo", latitud = -12.1534, longitud = -76.9508, orden = 4),
                Estacion(nombre = "El Sol", linea = "Línea 1", distrito = "San Juan de Miraflores", latitud = -12.1423, longitud = -76.9612, orden = 5),
                Estacion(nombre = "Atocongo", linea = "Línea 1", distrito = "San Juan de Miraflores", latitud = -12.1289, longitud = -76.9678, orden = 6),
                Estacion(nombre = "Jorge Chávez", linea = "Línea 1", distrito = "Santiago de Surco", latitud = -12.1156, longitud = -76.9734, orden = 7),
                Estacion(nombre = "Ayacucho", linea = "Línea 1", distrito = "Santiago de Surco", latitud = -12.1045, longitud = -76.9789, orden = 8),
                Estacion(nombre = "Cabitos", linea = "Línea 1", distrito = "San Borja", latitud = -12.0923, longitud = -76.9845, orden = 9),
                Estacion(nombre = "San Borja Sur", linea = "Línea 1", distrito = "San Borja", latitud = -12.0834, longitud = -76.9912, orden = 10),
                Estacion(nombre = "Angamos", linea = "Línea 1", distrito = "Surquillo", latitud = -12.0745, longitud = -76.9967, orden = 11),
                Estacion(nombre = "La Cultura", linea = "Línea 1", distrito = "San Borja", latitud = -12.0678, longitud = -77.0023, orden = 12),
                Estacion(nombre = "Arriola", linea = "Línea 1", distrito = "La Victoria", latitud = -12.0589, longitud = -77.0089, orden = 13),
                Estacion(nombre = "Gamarra", linea = "Línea 1", distrito = "La Victoria", latitud = -12.0512, longitud = -77.0145, orden = 14),
                Estacion(nombre = "Miguel Grau", linea = "Línea 1", distrito = "Lima", latitud = -12.0445, longitud = -77.0198, orden = 15),
                Estacion(nombre = "Presbítero Maestro", linea = "Línea 1", distrito = "Lima", latitud = -12.0378, longitud = -77.0256, orden = 16),
                Estacion(nombre = "Caja De Agua", linea = "Línea 1", distrito = "Lima", latitud = -12.0312, longitud = -77.0312, orden = 17),
                Estacion(nombre = "Pirámide del Sol", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -12.0245, longitud = -77.0367, orden = 18),
                Estacion(nombre = "Los Jardines", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -12.0178, longitud = -77.0423, orden = 19),
                Estacion(nombre = "Los Postes", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -12.0112, longitud = -77.0478, orden = 20),
                Estacion(nombre = "San Carlos", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -12.0045, longitud = -77.0534, orden = 21),
                Estacion(nombre = "San Martín", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -11.9978, longitud = -77.0589, orden = 22),
                Estacion(nombre = "Santa Rosa", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -11.9912, longitud = -77.0645, orden = 23),
                Estacion(nombre = "Bayóvar", linea = "Línea 1", distrito = "San Juan de Lurigancho", latitud = -11.9845, longitud = -77.0701, orden = 24)
            )
            estacionDao.insertarVarias(estaciones)
        }
    }
}