package com.proyecto.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.proyecto.myapplication.data.dao.ConfiguracionDAO
import com.proyecto.myapplication.data.dao.EstacionDAO
import com.proyecto.myapplication.data.dao.RutaDAO
import com.proyecto.myapplication.data.model.Configuracion
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.data.model.Ruta

@Database(
    entities = [
        Configuracion::class,
        Estacion::class,
        Ruta::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MetroLimaDatabase : RoomDatabase() {

    abstract fun configuracionDao(): ConfiguracionDAO
    abstract fun estacionDao(): EstacionDAO
    abstract fun rutaDao(): RutaDAO

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
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}