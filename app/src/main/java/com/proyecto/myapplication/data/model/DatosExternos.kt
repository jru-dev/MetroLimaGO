package com.proyecto.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DatosExternos(
    val alertas: List<Alerta>,
    val horariosActualizados: List<HorarioActualizado>,
    val ultimaActualizacion: Long
)

@Entity(tableName = "alertas")
data class Alerta(
    @PrimaryKey
    val id: String,
    val titulo: String,
    val descripcion: String,
    val tipo: TipoAlerta,
    val estacionesAfectadas: List<String>,
    val fechaInicio: Long,
    val fechaFin: Long
)

data class HorarioActualizado(
    val estacionId: String,
    val horarioApertura: String,
    val horarioCierre: String,
    val frecuencia: Int // minutos entre trenes
)

enum class TipoAlerta {
    MANTENIMIENTO,
    RETRASO,
    SUSPENSION,
    INFORMACION
}