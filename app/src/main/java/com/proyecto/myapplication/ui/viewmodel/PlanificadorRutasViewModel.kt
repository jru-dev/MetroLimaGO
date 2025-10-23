package com.proyecto.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.data.model.Ruta
import com.proyecto.myapplication.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanificadorRutasViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PlanificadorRutasUiState())
    val uiState: StateFlow<PlanificadorRutasUiState> = _uiState.asStateFlow()
    
    fun selectEstacionOrigen(estacion: Estacion) {
        _uiState.value = _uiState.value.copy(
            estacionOrigen = estacion,
            error = null
        )
    }
    
    fun selectEstacionDestino(estacion: Estacion) {
        _uiState.value = _uiState.value.copy(
            estacionDestino = estacion,
            error = null
        )
    }
    
    fun swapEstaciones() {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            estacionOrigen = currentState.estacionDestino,
            estacionDestino = currentState.estacionOrigen
        )
    }
    
    fun calcularRuta() {
        val currentState = _uiState.value
        val origen = currentState.estacionOrigen
        val destino = currentState.estacionDestino
        
        if (origen == null || destino == null) {
            _uiState.value = currentState.copy(
                error = "Selecciona estación de origen y destino"
            )
            return
        }
        
        if (origen.id == destino.id) {
            _uiState.value = currentState.copy(
                error = "La estación de origen y destino deben ser diferentes"
            )
            return
        }
        
        viewModelScope.launch {
            _uiState.value = currentState.copy(isCalculando = true, error = null)
            
            try {
                // Simular cálculo de ruta (en una app real, esto sería un algoritmo complejo)
                val rutasCalculadas = calcularRutasSimuladas(origen, destino)
                
                _uiState.value = currentState.copy(
                    rutasCalculadas = rutasCalculadas,
                    isCalculando = false
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    error = e.message ?: "Error al calcular la ruta",
                    isCalculando = false
                )
            }
        }
    }
    
    fun guardarComoFavorita(ruta: Ruta) {
        viewModelScope.launch {
            try {
                val rutaFavorita = ruta.copy(esFavorita = !ruta.esFavorita)
                repository.updateRuta(rutaFavorita)
                
                // Actualizar la lista de rutas calculadas
                val rutasActualizadas = _uiState.value.rutasCalculadas.map { 
                    if (it.id == ruta.id) rutaFavorita else it 
                }
                _uiState.value = _uiState.value.copy(rutasCalculadas = rutasActualizadas)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al guardar como favorita"
                )
            }
        }
    }
    
    private fun calcularRutasSimuladas(origen: Estacion, destino: Estacion): List<Ruta> {
        // Simulación de cálculo de rutas
        val tiempoEstimado = kotlin.random.Random.nextInt(15, 60)
        val estacionesIntermedias = listOf("est_intermedia_1", "est_intermedia_2", "est_intermedia_3")
        
        val ruta1 = Ruta(
            id = "ruta_calc_1",
            estacionOrigen = origen.id,
            estacionDestino = destino.id,
            tiempoEstimado = tiempoEstimado,
            estacionesIntermedias = estacionesIntermedias,
            esFavorita = false
        )
        
        val ruta2 = Ruta(
            id = "ruta_calc_2",
            estacionOrigen = origen.id,
            estacionDestino = destino.id,
            tiempoEstimado = tiempoEstimado + 10,
            estacionesIntermedias = estacionesIntermedias + listOf("est_intermedia_4"),
            esFavorita = false
        )
        
        return listOf(ruta1, ruta2)
    }
}

data class PlanificadorRutasUiState(
    val estacionOrigen: Estacion? = null,
    val estacionDestino: Estacion? = null,
    val isCalculando: Boolean = false,
    val error: String? = null,
    val rutasCalculadas: List<Ruta> = emptyList()
)
