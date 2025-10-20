package com.proyecto.myapplication.screens.estaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.repository.EstacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar la lógica de negocio de las estaciones
 */
class EstacionesViewModel(private val repository: EstacionRepository) : ViewModel() {

    // Lista de todas las estaciones
    private val _estaciones = MutableStateFlow<List<Estacion>>(emptyList())
    val estaciones: StateFlow<List<Estacion>> = _estaciones.asStateFlow()

    // Estación seleccionada para ver detalles
    private val _estacionSeleccionada = MutableStateFlow<Estacion?>(null)
    val estacionSeleccionada: StateFlow<Estacion?> = _estacionSeleccionada.asStateFlow()

    // Query de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarEstaciones()
    }

    /**
     * Carga todas las estaciones desde el repositorio
     */
    private fun cargarEstaciones() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.todasLasEstaciones.collect { listaEstaciones ->
                _estaciones.value = listaEstaciones
                _isLoading.value = false
            }
        }
    }

    /**
     * Busca estaciones por nombre
     */
    fun buscarEstaciones(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isEmpty()) {
                repository.todasLasEstaciones.collect { listaEstaciones ->
                    _estaciones.value = listaEstaciones
                }
            } else {
                repository.buscarPorNombre(query).collect { listaEstaciones ->
                    _estaciones.value = listaEstaciones
                }
            }
        }
    }

    /**
     * Obtiene una estación por su ID
     */
    fun obtenerEstacionPorId(id: Int) {
        viewModelScope.launch {
            val estacion = repository.obtenerPorId(id)
            _estacionSeleccionada.value = estacion
        }
    }

    /**
     * Inserta una nueva estación
     */
    fun insertarEstacion(estacion: Estacion) {
        viewModelScope.launch {
            repository.insertar(estacion)
        }
    }

    /**
     * Actualiza una estación existente
     */
    fun actualizarEstacion(estacion: Estacion) {
        viewModelScope.launch {
            repository.actualizar(estacion)
        }
    }

    fun eliminarEstacion(estacion: Estacion) {
        viewModelScope.launch {
            repository.eliminar(estacion)
        }
    }
}

/**
 * Factory para crear el ViewModel con dependencias
 */
class EstacionesViewModelFactory(
    private val repository: EstacionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstacionesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstacionesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}