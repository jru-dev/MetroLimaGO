package com.proyecto.myapplication.data.repository

import com.proyecto.myapplication.data.dao.RutaDAO
import com.proyecto.myapplication.data.model.Ruta
import kotlinx.coroutines.flow.Flow

class RutaRepository(private val rutaDao: RutaDAO) {

    fun obtenerTodasLasRutas(): Flow<List<Ruta>> {
        return rutaDao.listarTodasRutas()
    }

    fun obtenerRutasFavoritas(): Flow<List<Ruta>> {
        return rutaDao.listarRutasFavoritas()
    }

    suspend fun obtenerRutaPorId(rutaId: Int): Ruta? {
        return rutaDao.obtenerRutaPorId(rutaId)
    }

    suspend fun insertarRuta(ruta: Ruta): Long {
        return rutaDao.insertarRuta(ruta)
    }

    suspend fun actualizarRuta(ruta: Ruta) {
        rutaDao.actualizarRuta(ruta)
    }

    suspend fun actualizarFavorito(rutaId: Int, esFavorito: Boolean) {
        rutaDao.actualizarFavorito(rutaId, esFavorito)
    }

    suspend fun eliminarRuta(ruta: Ruta) {
        rutaDao.eliminarRuta(ruta)
    }

    suspend fun eliminarRutaPorId(rutaId: Int) {
        rutaDao.eliminarRutaPorId(rutaId)
    }
}