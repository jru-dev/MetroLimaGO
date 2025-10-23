package com.proyecto.myapplication.data.dao

import androidx.room.*
import com.proyecto.myapplication.data.model.Configuracion
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(configuracion: Configuracion)

    @Update
    suspend fun actualizar(configuracion: Configuracion)

    @Delete
    suspend fun eliminar(configuracion: Configuracion)

    @Query("SELECT * FROM configuracion WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Configuracion?

    @Query("SELECT * FROM configuracion")
    fun obtenerTodas(): Flow<List<Configuracion>>

    @Query("SELECT * FROM configuracion LIMIT 1")
    suspend fun obtenerConfiguracionActual(): Configuracion?

    @Query("DELETE FROM configuracion")
    suspend fun eliminarTodas()
}