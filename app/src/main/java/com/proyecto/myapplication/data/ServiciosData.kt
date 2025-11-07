package com.proyecto.myapplication.data

import com.proyecto.myapplication.data.model.ServicioEstacion
import com.proyecto.myapplication.data.model.TipoServicio

object ServiciosData {
    // Servicios mock para diferentes estaciones
    fun getServiciosPorEstacion(estacionId: String): List<ServicioEstacion> {
        return when (estacionId) {
            "L1_16", "L1_17" -> listOf(
                ServicioEstacion("S1", estacionId, TipoServicio.RESTAURANTE, "KFC", null, 150),
                ServicioEstacion("S2", estacionId, TipoServicio.BANCO, "BCP", null, 200),
                ServicioEstacion("S3", estacionId, TipoServicio.FARMACIA, "InkaFarma", null, 300),
                ServicioEstacion("S4", estacionId, TipoServicio.UNIVERSIDAD, "Universidad Nacional Mayor de San Marcos", null, 500),
                ServicioEstacion("S5", estacionId, TipoServicio.COMERCIO, "Centro Comercial", null, 250)
            )
            "L1_12", "L1_13" -> listOf(
                ServicioEstacion("S6", estacionId, TipoServicio.RESTAURANTE, "McDonald's", null, 180),
                ServicioEstacion("S7", estacionId, TipoServicio.BANCO, "BBVA", null, 220),
                ServicioEstacion("S8", estacionId, TipoServicio.FARMACIA, "Boticas y Salud", null, 280),
                ServicioEstacion("S9", estacionId, TipoServicio.HOSPITAL, "Clínica San Borja", null, 600)
            )
            "L1_08", "L1_09", "L1_10" -> listOf(
                ServicioEstacion("S10", estacionId, TipoServicio.RESTAURANTE, "Pizza Hut", null, 160),
                ServicioEstacion("S11", estacionId, TipoServicio.BANCO, "Interbank", null, 190),
                ServicioEstacion("S12", estacionId, TipoServicio.UNIVERSIDAD, "Universidad de Lima", null, 800),
                ServicioEstacion("S13", estacionId, TipoServicio.PARQUE, "Parque Kennedy", null, 400)
            )
            else -> listOf(
                ServicioEstacion("S14", estacionId, TipoServicio.RESTAURANTE, "Restaurante Local", null, 200),
                ServicioEstacion("S15", estacionId, TipoServicio.BANCO, "Banco", null, 250),
                ServicioEstacion("S16", estacionId, TipoServicio.FARMACIA, "Farmacia", null, 300)
            )
        }
    }
}

