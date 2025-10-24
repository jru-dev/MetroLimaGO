package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estaciones")
data class Estacion(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val latitud: Double,
    val longitud: Double,
    val horarioApertura: String,
    val horarioCierre: String,
    val tieneAcceso: Boolean = true,
    val tieneEstacionamiento: Boolean = false,
    val tieneBicicletero: Boolean = false
)