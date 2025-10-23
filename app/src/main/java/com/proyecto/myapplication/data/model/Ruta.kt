package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutas")
data class Ruta(
    @PrimaryKey
    val id: String,
    val estacionOrigen: String,
    val estacionDestino: String,
    val tiempoEstimado: Int, // en minutos
    val estacionesIntermedias: List<String>,
    val esFavorita: Boolean = false,
    val fechaCreacion: Long = System.currentTimeMillis()
)