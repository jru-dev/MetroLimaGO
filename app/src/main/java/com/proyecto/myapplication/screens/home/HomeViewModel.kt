package com.proyecto.myapplication.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    enum class EstadoServicio { NORMAL, DEMORAS, FUERA_DE_SERVICIO }

    private val _estadoServicio = MutableStateFlow(EstadoServicio.FUERA_DE_SERVICIO)
    val estadoServicio: StateFlow<EstadoServicio> = _estadoServicio

    fun getMensajeEstado(): String {
        return when (_estadoServicio.value) {
            EstadoServicio.NORMAL -> "Servicio operativo con normalidad"
            EstadoServicio.DEMORAS -> "Demoras por mantenimiento leve en Línea 1"
            EstadoServicio.FUERA_DE_SERVICIO -> "Línea 1 fuera de servicio por mantenimiento de emergencia"
        }
    }

    fun getTiempoReparacion(): String {
        return when (_estadoServicio.value) {
            EstadoServicio.FUERA_DE_SERVICIO -> "Tiempo estimado de reparación: 45 minutos"
            EstadoServicio.DEMORAS -> "Servicio demorando entre 5 y 10 minutos"
            else -> "Operando con normalidad"
        }
    }
}