package com.proyecto.myapplication.data.local

import androidx.room.*
import com.proyecto.myapplication.data.model.Ruta
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaDao {
    @Query("SELECT * FROM rutas ORDER BY fechaCreacion DESC")
    fun getAllRutas(): Flow<List<Ruta>>
    
    @Query("SELECT * FROM rutas WHERE esFavorita = 1 ORDER BY fechaCreacion DESC")
    fun getRutasFavoritas(): Flow<List<Ruta>>
    
    @Query("SELECT * FROM rutas WHERE id = :id")
    suspend fun getRutaById(id: String): Ruta?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRuta(ruta: Ruta)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRutas(rutas: List<Ruta>)
    
    @Update
    suspend fun updateRuta(ruta: Ruta)
    
    @Delete
    suspend fun deleteRuta(ruta: Ruta)
    
    @Query("DELETE FROM rutas")
    suspend fun deleteAllRutas()
}
