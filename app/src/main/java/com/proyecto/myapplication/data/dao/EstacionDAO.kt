package com.proyecto.myapplication.data.dao

import androidx.room.*
import com.proyecto.myapplication.data.model.Estacion
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object para operaciones de base de datos con Estaciones
 */
@Dao
interface EstacionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(estacion: Estacion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarias(estaciones: List<Estacion>)

    @Update
    suspend fun actualizar(estacion: Estacion)

    @Delete
    suspend fun eliminar(estacion: Estacion)

    @Query("SELECT * FROM estaciones ORDER BY orden ASC")
    fun listarTodas(): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Estacion?

    @Query("SELECT * FROM estaciones WHERE nombre LIKE '%' || :nombre || '%' ORDER BY orden ASC")
    fun buscarPorNombre(nombre: String): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE linea = :linea ORDER BY orden ASC")
    fun buscarPorLinea(linea: String): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE distrito LIKE '%' || :distrito || '%' ORDER BY orden ASC")
    fun buscarPorDistrito(distrito: String): Flow<List<Estacion>>

    @Query("SELECT COUNT(*) FROM estaciones")
    suspend fun contarEstaciones(): Int

    @Query("DELETE FROM estaciones")
    suspend fun eliminarTodas()
}