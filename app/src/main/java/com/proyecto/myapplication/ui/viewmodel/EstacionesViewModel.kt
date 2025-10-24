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
class EstacionesViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EstacionesUiState())
    val uiState: StateFlow<EstacionesUiState> = _uiState.asStateFlow()
    
    init {
        loadEstaciones()
    }
    
    private fun loadEstaciones() {
        viewModelScope.launch {
            try {
                repository.getAllEstaciones().collect { estaciones ->
                    _uiState.value = _uiState.value.copy(
                        estaciones = estaciones,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al cargar estaciones",
                    isLoading = false
                )
            }
        }
    }
    
    fun searchEstaciones(query: String) {
        viewModelScope.launch {
            try {
                if (query.isBlank()) {
                    loadEstaciones()
                } else {
                    repository.searchEstaciones(query).collect { estaciones ->
                        _uiState.value = _uiState.value.copy(
                            estaciones = estaciones,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al buscar estaciones",
                    isLoading = false
                )
            }
        }
    }
    
    fun filterByLinea(linea: String) {
        viewModelScope.launch {
            try {
                repository.getEstacionesByLinea(linea).collect { estaciones ->
                    _uiState.value = _uiState.value.copy(
                        estaciones = estaciones,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al filtrar estaciones",
                    isLoading = false
                )
            }
        }
    }
}

data class EstacionesUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val estaciones: List<Estacion> = emptyList()
)
