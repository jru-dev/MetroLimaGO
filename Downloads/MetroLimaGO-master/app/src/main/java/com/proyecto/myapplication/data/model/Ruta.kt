package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definición de la entidad Ruta para la base de datos local (Room)
@Entity(tableName = "rutas")
data class Ruta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID único para la ruta (auto generado)
    val nombre: String, // Nombre de la ruta
    val origen: String, // Estación de origen de la ruta
    val destino: String, // Estación de destino de la ruta
    val duracion: String, // Duración aproximada de la ruta
    val distancia: String // Distancia de la ruta
)