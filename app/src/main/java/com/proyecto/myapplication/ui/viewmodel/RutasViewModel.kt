package com.proyecto.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Ruta
import com.proyecto.myapplication.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RutasViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RutasUiState())
    val uiState: StateFlow<RutasUiState> = _uiState.asStateFlow()
    
    init {
        loadRutas()
    }
    
    private fun loadRutas() {
        viewModelScope.launch {
            try {
                repository.getAllRutas().collect { rutas ->
                    _uiState.value = _uiState.value.copy(
                        rutas = rutas,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al cargar rutas",
                    isLoading = false
                )
            }
        }
    }
}

data class RutasUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val rutas: List<Ruta> = emptyList()
)
