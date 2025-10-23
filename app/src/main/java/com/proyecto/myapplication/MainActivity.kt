package com.proyecto.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.myapplication.screens.configuracion.ConfiguracionActivity
import com.proyecto.myapplication.screens.datosExternos.DatosExternosActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Crear UI programáticamente para evitar problemas con ViewBinding
        setContentView(createSimpleLayout())
    }
    
    private fun createSimpleLayout(): android.view.View {
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }
        
        val title = TextView(this).apply {
            text = "MetroLimaGO"
            textSize = 24f
            setPadding(0, 0, 0, 50)
        }
        
        val btnDatosExternos = Button(this).apply {
            text = "Datos Externos"
            setOnClickListener {
                val intent = Intent(this@MainActivity, DatosExternosActivity::class.java)
                startActivity(intent)
            }
        }
        
        val btnConfiguracion = Button(this).apply {
            text = "Configuración"
            setOnClickListener {
                val intent = Intent(this@MainActivity, ConfiguracionActivity::class.java)
                startActivity(intent)
            }
        }
        
        layout.addView(title)
        layout.addView(btnDatosExternos)
        layout.addView(btnConfiguracion)
        
        return layout
    }
}