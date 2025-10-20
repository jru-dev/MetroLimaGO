package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa una estación del Metro de Lima - Línea 1
 */
@Entity(tableName = "estaciones")
data class Estacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val linea: String = "Línea 1",

    val distrito: String,

    val latitud: Double,

    val longitud: Double,

    val horario: String = "6:00 AM - 10:00 PM",

    val orden: Int // Orden de la estación en la línea (1-24)
)