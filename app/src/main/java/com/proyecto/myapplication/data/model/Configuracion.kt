package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuracion")
data class Configuracion(
    @PrimaryKey
    val id: Int = 1,
    val modoOscuro: Boolean = false,
    val idioma: String = "es",
    val notificaciones: Boolean = true,
    val tema: String = "sistema"
)