package com.proyecto.myapplication.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.data.model.Ruta
import com.proyecto.myapplication.data.model.Configuracion
import com.proyecto.myapplication.data.model.Alerta

@Database(
    entities = [Estacion::class, Ruta::class, Configuracion::class, Alerta::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MetroDatabase : RoomDatabase() {
    abstract fun estacionDao(): EstacionDao
    abstract fun rutaDao(): RutaDao
    abstract fun configuracionDao(): ConfiguracionDao
    abstract fun alertaDao(): AlertaDao
    
    companion object {
        @Volatile
        private var INSTANCE: MetroDatabase? = null
        
        fun getDatabase(context: Context): MetroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDatabase::class.java,
                    "metro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
