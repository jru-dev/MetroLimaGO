package com.proyecto.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.proyecto.myapplication.data.entity.Estacion

@Dao
interface EstacionDao {

    // Insertar una nueva estación en la base de datos
    @Insert
    suspend fun insertarEstacion(estacion: Estacion)

    // Obtener todas las estaciones almacenadas
    @Query("SELECT * FROM estaciones")
    suspend fun obtenerTodasLasEstaciones(): List<Estacion>

    // Obtener una estación por su ID. Si no existe, retornará null.
    @Query("SELECT * FROM estaciones WHERE id = :id LIMIT 1")
    suspend fun obtenerEstacionPorId(id: Long): Estacion?

    // Actualizar los datos de una estación existente
    @Update
    suspend fun actualizarEstacion(estacion: Estacion)

    // Eliminar una estación de la base de datos
    @Delete
    suspend fun eliminarEstacion(estacion: Estacion)

    // Eliminar todas las estaciones. Útil si se necesita limpiar la base de datos.
    @Query("DELETE FROM estaciones")
    suspend fun eliminarTodasLasEstaciones()

    // Contar el número total de estaciones en la base de datos
    @Query("SELECT COUNT(*) FROM estaciones")
    suspend fun contarEstaciones(): Int
}
