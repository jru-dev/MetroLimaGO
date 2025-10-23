package com.proyecto.myapplication.screens.datosExternos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.proyecto.myapplication.databinding.ActivityDatosExternosBinding
import com.proyecto.myapplication.repository.DatosExternosRepository
import com.proyecto.myapplication.utils.PreferenceHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DatosExternosActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDatosExternosBinding
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var repository: DatosExternosRepository
    
    private val viewModel: DatosExternosViewModel by viewModels {
        DatosExternosViewModelFactory(
            PreferenceHelper(this),
            DatosExternosRepository(PreferenceHelper(this))
        )
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatosExternosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferenceHelper = PreferenceHelper(this)
        repository = DatosExternosRepository(preferenceHelper)
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Configurar toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Datos Externos"
        
        // Configurar botones
        binding.btnSincronizar.setOnClickListener {
            mostrarDialogoSincronizacion()
        }
        
        binding.btnConfiguracion.setOnClickListener {
            mostrarDialogoConfiguracion()
        }
        
        binding.btnLimpiarCache.setOnClickListener {
            mostrarDialogoLimpiarCache()
        }
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                actualizarUI(state)
            }
        }
    }
    
    private fun actualizarUI(state: DatosExternosUiState) {
        // Mostrar/ocultar loading
        binding.progressBar.visibility = if (state.isLoading) {
            android.view.View.VISIBLE
        } else {
            android.view.View.GONE
        }
        
        // Actualizar información de sincronización
        if (state.ultimaSincronizacion > 0) {
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val fecha = Date(state.ultimaSincronizacion)
            binding.tvUltimaSincronizacion.text = "Última sincronización: ${formatter.format(fecha)}"
        } else {
            binding.tvUltimaSincronizacion.text = "Nunca sincronizado"
        }
        
        // Actualizar versión
        binding.tvVersion.text = "Versión: ${state.version}"
        
        // Mostrar datos si están disponibles
        state.datos?.let { datos ->
            binding.tvDatosDisponibles.text = "Datos disponibles: ${datos.horarios.size} horarios"
            binding.tvUltimaActualizacion.text = "Actualización: ${datos.ultimaActualizacion}"
        }
        
        // Mostrar error si existe
        state.error?.let { error ->
            mostrarError(error)
        }
    }
    
    private fun mostrarDialogoSincronizacion() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Sincronizar Datos")
            .setMessage("¿Desea sincronizar los datos externos del metro? Esto puede tomar unos momentos.")
            .setPositiveButton("Sincronizar") { _, _ ->
                viewModel.sincronizarDatos()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun mostrarDialogoConfiguracion() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Sincronizar Configuración")
            .setMessage("¿Desea sincronizar la configuración desde el servidor?")
            .setPositiveButton("Sincronizar") { _, _ ->
                viewModel.sincronizarConfiguracion()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun mostrarDialogoLimpiarCache() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Limpiar Cache")
            .setMessage("¿Desea limpiar el cache de datos? Esto eliminará los datos almacenados localmente.")
            .setPositiveButton("Limpiar") { _, _ ->
                limpiarCache()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun limpiarCache() {
        // Aquí implementarías la lógica para limpiar el cache
        Toast.makeText(this, "Cache limpiado", Toast.LENGTH_SHORT).show()
    }
    
    private fun mostrarError(mensaje: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("Reintentar") { _, _ ->
                viewModel.sincronizarDatos()
            }
            .setNegativeButton("Cerrar") { _, _ ->
                viewModel.limpiarError()
            }
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}