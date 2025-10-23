package com.proyecto.myapplication.screens.configuracion

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.proyecto.myapplication.databinding.ActivityConfiguracionBinding
import com.proyecto.myapplication.utils.PreferenceHelper
import kotlinx.coroutines.launch

class ConfiguracionActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityConfiguracionBinding
    private lateinit var preferenceHelper: PreferenceHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferenceHelper = PreferenceHelper(this)
        
        setupUI()
        cargarConfiguracionActual()
    }
    
    private fun setupUI() {
        // Configurar toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Configuración"
        
        // Configurar switch de modo oscuro
        binding.switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            cambiarModoOscuro(isChecked)
        }
        
        // Configurar selector de idioma
        binding.btnIdioma.setOnClickListener {
            mostrarSelectorIdioma()
        }
        
        // Configurar switch de notificaciones
        binding.switchNotificaciones.setOnCheckedChangeListener { _, isChecked ->
            preferenceHelper.setNotificaciones(isChecked)
            mostrarMensaje("Notificaciones ${if (isChecked) "activadas" else "desactivadas"}")
        }
        
        // Configurar selector de tema
        binding.btnTema.setOnClickListener {
            mostrarSelectorTema()
        }
        
        // Configurar botón de información
        binding.btnInformacion.setOnClickListener {
            mostrarInformacion()
        }
        
        // Configurar botón de créditos
        binding.btnCreditos.setOnClickListener {
            mostrarCreditos()
        }
    }
    
    private fun cargarConfiguracionActual() {
        // Cargar configuración actual
        binding.switchModoOscuro.isChecked = preferenceHelper.getModoOscuro()
        binding.switchNotificaciones.isChecked = preferenceHelper.getNotificaciones()
        
        // Actualizar textos
        binding.tvIdiomaActual.text = "Idioma: ${obtenerNombreIdioma(preferenceHelper.getIdioma())}"
        binding.tvTemaActual.text = "Tema: ${obtenerNombreTema(preferenceHelper.getTema())}"
        binding.tvVersion.text = "Versión: ${preferenceHelper.getVersion()}"
    }
    
    private fun cambiarModoOscuro(activo: Boolean) {
        preferenceHelper.setModoOscuro(activo)
        
        val modo = if (activo) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        
        AppCompatDelegate.setDefaultNightMode(modo)
        mostrarMensaje("Modo ${if (activo) "oscuro" else "claro"} activado")
    }
    
    private fun mostrarSelectorIdioma() {
        val idiomas = arrayOf("Español", "English")
        val idiomasCodigos = arrayOf("es", "en")
        val idiomaActual = preferenceHelper.getIdioma()
        val indiceActual = idiomasCodigos.indexOf(idiomaActual)
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Seleccionar Idioma")
            .setSingleChoiceItems(idiomas, indiceActual) { dialog, which ->
                val idiomaSeleccionado = idiomasCodigos[which]
                preferenceHelper.setIdioma(idiomaSeleccionado)
                binding.tvIdiomaActual.text = "Idioma: ${idiomas[which]}"
                mostrarMensaje("Idioma cambiado a ${idiomas[which]}")
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun mostrarSelectorTema() {
        val temas = arrayOf("Por defecto", "Metro Lima", "Minimalista")
        val temasCodigos = arrayOf("default", "metro", "minimal")
        val temaActual = preferenceHelper.getTema()
        val indiceActual = temasCodigos.indexOf(temaActual)
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Seleccionar Tema")
            .setSingleChoiceItems(temas, indiceActual) { dialog, which ->
                val temaSeleccionado = temasCodigos[which]
                preferenceHelper.setTema(temaSeleccionado)
                binding.tvTemaActual.text = "Tema: ${temas[which]}"
                mostrarMensaje("Tema cambiado a ${temas[which]}")
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun mostrarInformacion() {
        val informacion = """
            MetroLimaGo v${preferenceHelper.getVersion()}
            
            Aplicación oficial del Metro de Lima
            Desarrollada para facilitar el transporte público
            
            Características:
            • Horarios en tiempo real
            • Rutas optimizadas
            • Notificaciones de servicio
            • Múltiples idiomas
            • Temas personalizables
            
            Última actualización: ${obtenerFechaActual()}
        """.trimIndent()
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Información de la Aplicación")
            .setMessage(informacion)
            .setPositiveButton("Cerrar", null)
            .show()
    }
    
    private fun mostrarCreditos() {
        val creditos = """
            MetroLimaGo - Créditos
            
            Desarrollado por:
            • Equipo de Desarrollo MetroLimaGo
            • Universidad Nacional de Ingeniería
            
            Tecnologías utilizadas:
            • Android Studio
            • Kotlin
            • Retrofit
            • Room Database
            • Material Design
            
            Datos proporcionados por:
            • Autoridad Autónoma del Sistema Eléctrico de Transporte Masivo de Lima y Callao
            
            Agradecimientos especiales:
            • Comunidad de desarrolladores Android
            • Usuarios beta del Metro de Lima
            
            © 2024 MetroLimaGo. Todos los derechos reservados.
        """.trimIndent()
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Créditos")
            .setMessage(creditos)
            .setPositiveButton("Cerrar", null)
            .show()
    }
    
    private fun obtenerNombreIdioma(codigo: String): String {
        return when (codigo) {
            "es" -> "Español"
            "en" -> "English"
            else -> "Español"
        }
    }
    
    private fun obtenerNombreTema(codigo: String): String {
        return when (codigo) {
            "default" -> "Por defecto"
            "metro" -> "Metro Lima"
            "minimal" -> "Minimalista"
            else -> "Por defecto"
        }
    }
    
    private fun obtenerFechaActual(): String {
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return formatter.format(java.util.Date())
    }
    
    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}