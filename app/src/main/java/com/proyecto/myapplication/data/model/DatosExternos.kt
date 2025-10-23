package com.proyecto.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class DatosExternos(
    @SerializedName("horarios")
    val horarios: List<Horario>,
    @SerializedName("ultima_actualizacion")
    val ultimaActualizacion: String,
    @SerializedName("version")
    val version: String
)

data class Horario(
    @SerializedName("linea")
    val linea: String,
    @SerializedName("estacion")
    val estacion: String,
    @SerializedName("direccion")
    val direccion: String,
    @SerializedName("horarios_salida")
    val horariosSalida: List<String>,
    @SerializedName("frecuencia")
    val frecuencia: Int,
    @SerializedName("estado")
    val estado: String
)

data class ConfiguracionUsuario(
    val modoOscuro: Boolean = false,
    val idioma: String = "es",
    val notificaciones: Boolean = true,
    val tema: String = "default"
)