package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuracion")
data class Configuracion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idioma: String = "es", // español por defecto
    val modoOscuro: Boolean = false,
    val notificacionesActivas: Boolean = true,
    val unidadDistancia: String = "km", // km o millas
    val tamañoFuente: String = "medio", // pequeño, medio, grande
    val ultimaActualizacion: Long = System.currentTimeMillis()
)