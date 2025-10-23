package com.proyecto.myapplication.screens.datosExternos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.DatosExternos
import com.proyecto.myapplication.repository.DatosExternosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DatosExternosUiState(
    val isLoading: Boolean = false,
    val datos: DatosExternos? = null,
    val error: String? = null,
    val ultimaSincronizacion: Long = 0L,
    val version: String = "1.0"
)

class DatosExternosViewModel(
    private val repository: DatosExternosRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DatosExternosUiState())
    val uiState: StateFlow<DatosExternosUiState> = _uiState.asStateFlow()
    
    init {
        cargarDatosIniciales()
    }
    
    private fun cargarDatosIniciales() {
        _uiState.value = _uiState.value.copy(
            ultimaSincronizacion = repository.obtenerUltimaSincronizacion(),
            version = repository.obtenerVersion()
        )
    }
    
    fun sincronizarDatos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.obtenerHorarios()
                .onSuccess { datos ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        datos = datos,
                        ultimaSincronizacion = repository.obtenerUltimaSincronizacion()
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
        }
    }
    
    fun sincronizarConfiguracion() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.obtenerConfiguracion()
                .onSuccess { config ->
                    // Procesar configuración si es necesario
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al sincronizar configuración"
                    )
                }
        }
    }
    
    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun actualizarVersion(version: String) {
        _uiState.value = _uiState.value.copy(version = version)
    }
}