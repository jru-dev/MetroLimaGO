package com.proyecto.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.proyecto.myapplication.data.entity.Estacion

@Dao
interface EstacionDao {

    // Insertar una nueva estación
    @Insert
    suspend fun insertarEstacion(estacion: Estacion)

    // Obtener todas las estaciones
    @Query("SELECT * FROM estaciones")
    suspend fun obtenerTodasLasEstaciones(): List<Estacion>

    // Obtener una estación por su ID
    @Query("SELECT * FROM estaciones WHERE id = :id")
    suspend fun obtenerEstacionPorId(id: Long): Estacion?

    // Actualizar una estación
    @Update
    suspend fun actualizarEstacion(estacion: Estacion)

    // Eliminar una estación
    @Delete
    suspend fun eliminarEstacion(estacion: Estacion)
}
