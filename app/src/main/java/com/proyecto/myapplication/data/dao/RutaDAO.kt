package com.proyecto.myapplication.data.dao

import androidx.room.*
import com.proyecto.myapplication.data.model.Ruta
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRuta(ruta: Ruta): Long

    @Query("SELECT * FROM rutas ORDER BY id DESC")
    fun listarTodasRutas(): Flow<List<Ruta>>

    @Query("SELECT * FROM rutas WHERE favorito = 1 ORDER BY id DESC")
    fun listarRutasFavoritas(): Flow<List<Ruta>>

    @Query("SELECT * FROM rutas WHERE id = :rutaId")
    suspend fun obtenerRutaPorId(rutaId: Int): Ruta?

    @Update
    suspend fun actualizarRuta(ruta: Ruta)

    @Query("UPDATE rutas SET favorito = :esFavorito WHERE id = :rutaId")
    suspend fun actualizarFavorito(rutaId: Int, esFavorito: Boolean)

    @Delete
    suspend fun eliminarRuta(ruta: Ruta)

    @Query("DELETE FROM rutas WHERE id = :rutaId")
    suspend fun eliminarRutaPorId(rutaId: Int)
}