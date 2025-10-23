package com.proyecto.myapplication.data.dao

import androidx.room.*
import com.proyecto.myapplication.data.model.Estacion
import kotlinx.coroutines.flow.Flow

@Dao
interface EstacionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(estacion: Estacion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarias(estaciones: List<Estacion>)

    @Update
    suspend fun actualizar(estacion: Estacion)

    @Delete
    suspend fun eliminar(estacion: Estacion)

    @Query("SELECT * FROM estaciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Estacion?

    @Query("SELECT * FROM estaciones ORDER BY orden ASC, nombre ASC")
    fun obtenerTodas(): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE linea = :linea ORDER BY orden ASC")
    fun obtenerPorLinea(linea: String): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE favorita = 1 ORDER BY nombre ASC")
    fun obtenerFavoritas(): Flow<List<Estacion>>

    @Query("UPDATE estaciones SET favorita = :esFavorita WHERE id = :id")
    suspend fun actualizarFavorita(id: Int, esFavorita: Boolean)

    @Query("SELECT * FROM estaciones WHERE nombre LIKE '%' || :busqueda || '%' ORDER BY nombre ASC")
    fun buscarEstaciones(busqueda: String): Flow<List<Estacion>>

    @Query("DELETE FROM estaciones")
    suspend fun eliminarTodas()

    @Query("SELECT COUNT(*) FROM estaciones")
    suspend fun contarEstaciones(): Int

    @Query("SELECT * FROM estaciones WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerPorNombre(nombre: String): Estacion?

    @Query("SELECT DISTINCT linea FROM estaciones ORDER BY linea ASC")
    suspend fun obtenerLineasDisponibles(): List<String>
}