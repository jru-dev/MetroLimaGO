package com.proyecto.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Alerta
import com.proyecto.myapplication.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertasViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AlertasUiState())
    val uiState: StateFlow<AlertasUiState> = _uiState.asStateFlow()
    
    init {
        loadAlertas()
    }
    
    private fun loadAlertas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                repository.getAlertasActivas().collect { alertas ->
                    _uiState.value = _uiState.value.copy(
                        alertas = alertas,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al cargar alertas",
                    isLoading = false
                )
            }
        }
    }
    
    fun refreshAlertas() {
        loadAlertas()
    }
}

data class AlertasUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val alertas: List<Alerta> = emptyList()
)
