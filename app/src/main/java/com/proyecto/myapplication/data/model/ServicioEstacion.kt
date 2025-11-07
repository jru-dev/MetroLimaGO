package com.proyecto.myapplication.data.model

data class ServicioEstacion(
    val id: String,
    val estacionId: String,
    val tipo: TipoServicio,
    val nombre: String,
    val descripcion: String? = null,
    val distancia: Int = 0 // en metros
)

enum class TipoServicio {
    RESTAURANTE,
    BANCO,
    FARMACIA,
    UNIVERSIDAD,
    HOSPITAL,
    COMERCIO,
    HOTEL,
    PARQUE,
    OTRO
}

