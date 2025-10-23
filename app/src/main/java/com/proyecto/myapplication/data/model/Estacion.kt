package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estaciones")
data class Estacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val linea: String,
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = "",
    val horarioApertura: String = "06:00",
    val horarioCierre: String = "22:00",
    val tieneAscensor: Boolean = false,
    val tieneRampa: Boolean = false,
    val tieneBaño: Boolean = false,
    val favorita: Boolean = false,
    val orden: Int = 0
    val distanciaKm: Double = 0.0,
    val tiempoDesdeOrigen: Int = 0,
    val zona: String = ""
)