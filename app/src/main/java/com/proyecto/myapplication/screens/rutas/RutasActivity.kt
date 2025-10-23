package com.proyecto.myapplication.screens.rutas

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.proyecto.myapplication.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyecto.myapplication.data.model.Ruta
import com.proyecto.myapplication.data.repository.RutaRepository
import kotlinx.coroutines.launch
import kotlin.random.Random



class RutasViewModel(private val repository: RutaRepository) : ViewModel() {

    private val _tiempoEstimado = MutableLiveData<Int>()
    val tiempoEstimado: LiveData<Int> = _tiempoEstimado

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _estacionesIntermedias = MutableLiveData<List<String>>()
    val estacionesIntermedias: LiveData<List<String>> = _estacionesIntermedias

    fun calcularTiempoEstimado(origen: String, destino: String) {
        viewModelScope.launch {
            try {
                if (origen == destino) {
                    _mensaje.value = "El origen y destino no pueden ser iguales"
                    return@launch
                }

                val tiempoAleatorio = Random.nextInt(5, 26)
                _tiempoEstimado.value = tiempoAleatorio

                val estaciones = generarEstacionesIntermedias(origen, destino, tiempoAleatorio)
                _estacionesIntermedias.value = estaciones

                _mensaje.value = "Ruta calculada: $tiempoAleatorio minutos"
            } catch (e: Exception) {
                _mensaje.value = "Error al calcular la ruta: ${e.message}"
            }
        }
    }

    private fun generarEstacionesIntermedias(
        origen: String,
        destino: String,
        tiempo: Int
    ): List<String> {
        val numEstaciones = (tiempo / 3).coerceAtMost(8)
        val estaciones = mutableListOf<String>()

        for (i in 1..numEstaciones) {
            estaciones.add("Estación Intermedia $i")
        }

        return estaciones
    }

    fun guardarRuta(
        origen: String, destino: String, tiempoEstimado: Int,
        estaciones: List<String>, esFavorito: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                val ruta = Ruta(
                    origen = origen,
                    destino = destino,
                    tiempoEstimado = tiempoEstimado,
                    estacionesIntermedias = estaciones,
                    favorito = esFavorito
                )
                repository.insertarRuta(ruta)
                _mensaje.value = "Ruta guardada exitosamente"
            } catch (e: Exception) {
                _mensaje.value = "Error al guardar la ruta: ${e.message}"
            }
        }
    }
}
//Quiero que me ayudea a crear las pantallas principales e implementar regstro de usuarios e quiero que me ayude en implementar
//el inicio de seciony autenticacion
