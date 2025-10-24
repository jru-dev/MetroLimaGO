package com.proyecto.myapplication.data.local

import androidx.room.*
import com.proyecto.myapplication.data.model.Configuracion
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracionDao {
    @Query("SELECT * FROM configuracion WHERE id = 1")
    fun getConfiguracion(): Flow<Configuracion?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfiguracion(configuracion: Configuracion)
    
    @Update
    suspend fun updateConfiguracion(configuracion: Configuracion)
    
    @Query("UPDATE configuracion SET modoOscuro = :modoOscuro WHERE id = 1")
    suspend fun updateModoOscuro(modoOscuro: Boolean)
    
    @Query("UPDATE configuracion SET idioma = :idioma WHERE id = 1")
    suspend fun updateIdioma(idioma: String)
}
