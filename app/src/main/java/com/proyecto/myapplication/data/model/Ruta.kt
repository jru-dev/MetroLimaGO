package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.proyecto.myapplication.data.database.Converters

@Entity(tableName = "rutas")
@TypeConverters(Converters::class)
data class Ruta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val origen: String,

    val destino: String,

    val tiempoEstimado: Int, // en minutos

    val estacionesIntermedias: List<String>,

    val favorito: Boolean = false
)