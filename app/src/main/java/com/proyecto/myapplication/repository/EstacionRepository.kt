package com.proyecto.myapplication.repository

import com.proyecto.myapplication.data.dao.EstacionDAO
import com.proyecto.myapplication.data.model.Estacion
import kotlinx.coroutines.flow.Flow

class EstacionRepository(private val estacionDao: EstacionDAO) {

    val todasLasEstaciones: Flow<List<Estacion>> = estacionDao.listarTodas()

    suspend fun insertar(estacion: Estacion): Long {
        return estacionDao.insertar(estacion)
    }

    suspend fun insertarVarias(estaciones: List<Estacion>) {
        estacionDao.insertarVarias(estaciones)
    }

    suspend fun actualizar(estacion: Estacion) {
        estacionDao.actualizar(estacion)
    }

    suspend fun eliminar(estacion: Estacion) {
        estacionDao.eliminar(estacion)
    }

    suspend fun obtenerPorId(id: Int): Estacion? {
        return estacionDao.obtenerPorId(id)
    }

    fun buscarPorNombre(nombre: String): Flow<List<Estacion>> {
        return estacionDao.buscarPorNombre(nombre)
    }

    fun buscarPorLinea(linea: String): Flow<List<Estacion>> {
        return estacionDao.buscarPorLinea(linea)
    }

    fun buscarPorDistrito(distrito: String): Flow<List<Estacion>> {
        return estacionDao.buscarPorDistrito(distrito)
    }

    suspend fun contarEstaciones(): Int {
        return estacionDao.contarEstaciones()
    }

    suspend fun eliminarTodas() {
        estacionDao.eliminarTodas()
    }
}