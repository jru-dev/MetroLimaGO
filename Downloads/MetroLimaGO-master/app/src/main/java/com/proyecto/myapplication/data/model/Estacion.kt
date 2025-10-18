package com.proyecto.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definición de la entidad Estación para la base de datos local (Room)
@Entity(tableName = "estaciones")
data class Estacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID único para la estación (auto generado)
    val nombre: String, // Nombre de la estación
    val linea: String, // Línea a la que pertenece la estación
    val distrito: String, // Distrito en el que se encuentra la estación
    val coordenadas: String, // Coordenadas geográficas de la estación (latitud, longitud)
    val horario: String // Horario de funcionamiento de la estación
)
