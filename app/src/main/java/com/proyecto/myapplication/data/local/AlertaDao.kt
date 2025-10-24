package com.proyecto.myapplication.data.local

import androidx.room.*
import com.proyecto.myapplication.data.model.Alerta
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertaDao {
    @Query("SELECT * FROM alertas ORDER BY fechaInicio DESC")
    fun getAllAlertas(): Flow<List<Alerta>>
    
    @Query("SELECT * FROM alertas WHERE id = :id")
    suspend fun getAlertaById(id: String): Alerta?
    
    @Query("SELECT * FROM alertas WHERE tipo = :tipo ORDER BY fechaInicio DESC")
    fun getAlertasByTipo(tipo: String): Flow<List<Alerta>>
    
    @Query("SELECT * FROM alertas WHERE fechaInicio <= :currentTime AND fechaFin >= :currentTime ORDER BY fechaInicio DESC")
    fun getAlertasActivas(currentTime: Long): Flow<List<Alerta>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerta(alerta: Alerta)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlertas(alertas: List<Alerta>)
    
    @Update
    suspend fun updateAlerta(alerta: Alerta)
    
    @Delete
    suspend fun deleteAlerta(alerta: Alerta)
    
    @Query("DELETE FROM alertas")
    suspend fun deleteAllAlertas()
}
