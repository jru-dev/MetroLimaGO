package com.proyecto.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.proyecto.myapplication.data.entity.Configuracion

@Dao
interface ConfiguracionDao {

    // Insertar una nueva configuración en la base de datos
    @Insert
    suspend fun insertarConfiguracion(configuracion: Configuracion)

    // Obtener todas las configuraciones almacenadas
    @Query("SELECT * FROM configuraciones")
    suspend fun obtenerTodasLasConfiguraciones(): List<Configuracion>

    // Obtener una configuración por su ID. Si no existe, retornará null.
    @Query("SELECT * FROM configuraciones WHERE id = :id LIMIT 1")
    suspend fun obtenerConfiguracionPorId(id: Long): Configuracion?

    // Obtener la configuración actual (por ejemplo, una única fila representando la configuración global)
    @Query("SELECT * FROM configuraciones WHERE es_actual = 1 LIMIT 1")
    suspend fun obtenerConfiguracionActual(): Configuracion?

    // Actualizar los datos de una configuración existente
    @Update
    suspend fun actualizarConfiguracion(configuracion: Configuracion)

    // Eliminar una configuración de la base de datos
    @Delete
    suspend fun eliminarConfiguracion(configuracion: Configuracion)

    // Eliminar todas las configuraciones (si es necesario)
    @Query("DELETE FROM configuraciones")
    suspend fun eliminarTodasLasConfiguraciones()

    // Contar el número total de configuraciones en la base de datos
    @Query("SELECT COUNT(*) FROM configuraciones")
    suspend fun contarConfiguraciones(): Int
}
