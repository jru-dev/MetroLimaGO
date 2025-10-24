package com.proyecto.myapplication.data.local

import androidx.room.*
import com.proyecto.myapplication.data.model.Estacion
import kotlinx.coroutines.flow.Flow

@Dao
interface EstacionDao {
    @Query("SELECT * FROM estaciones ORDER BY nombre ASC")
    fun getAllEstaciones(): Flow<List<Estacion>>
    
    @Query("SELECT * FROM estaciones WHERE id = :id")
    suspend fun getEstacionById(id: String): Estacion?
    
    @Query("SELECT * FROM estaciones WHERE linea = :linea ORDER BY nombre ASC")
    fun getEstacionesByLinea(linea: String): Flow<List<Estacion>>
    
    @Query("SELECT * FROM estaciones WHERE nombre LIKE '%' || :query || '%' ORDER BY nombre ASC")
    fun searchEstaciones(query: String): Flow<List<Estacion>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstacion(estacion: Estacion)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstaciones(estaciones: List<Estacion>)
    
    @Update
    suspend fun updateEstacion(estacion: Estacion)
    
    @Delete
    suspend fun deleteEstacion(estacion: Estacion)
    
    @Query("DELETE FROM estaciones")
    suspend fun deleteAllEstaciones()
}
