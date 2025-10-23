package com.proyecto.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleEstacionViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DetalleEstacionUiState())
    val uiState: StateFlow<DetalleEstacionUiState> = _uiState.asStateFlow()
    
    fun loadEstacion(estacionId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val estacion = repository.getEstacionById(estacionId)
                _uiState.value = _uiState.value.copy(
                    estacion = estacion,
                    isLoading = false,
                    error = if (estacion == null) "Estación no encontrada" else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar la estación"
                )
            }
        }
    }
}

data class DetalleEstacionUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val estacion: Estacion? = null
)
