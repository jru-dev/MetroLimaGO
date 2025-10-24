package com.proyecto.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Configuracion
import com.proyecto.myapplication.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfiguracionViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ConfiguracionUiState())
    val uiState: StateFlow<ConfiguracionUiState> = _uiState.asStateFlow()
    
    init {
        loadConfiguracion()
    }
    
    private fun loadConfiguracion() {
        viewModelScope.launch {
            try {
                repository.getConfiguracion().collect { configuracion ->
                    _uiState.value = _uiState.value.copy(
                        configuracion = configuracion ?: Configuracion(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al cargar configuración",
                    isLoading = false
                )
            }
        }
    }
    
    fun updateModoOscuro(modoOscuro: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateModoOscuro(modoOscuro)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al actualizar modo oscuro"
                )
            }
        }
    }
    
    fun updateNotificaciones(notificaciones: Boolean) {
        viewModelScope.launch {
            try {
                val currentConfig = _uiState.value.configuracion ?: Configuracion()
                val updatedConfig = currentConfig.copy(notificaciones = notificaciones)
                repository.updateConfiguracion(updatedConfig)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al actualizar notificaciones"
                )
            }
        }
    }
}

data class ConfiguracionUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val configuracion: Configuracion? = null
)
