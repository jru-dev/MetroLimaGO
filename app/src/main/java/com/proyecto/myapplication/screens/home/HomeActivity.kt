package com.proyecto.myapplication.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.proyecto.myapplication.screens.configuracion.ConfiguracionActivity
import com.proyecto.myapplication.screens.estaciones.EstacionesActivity
import com.proyecto.myapplication.screens.rutas.RutasActivity

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                onNavigateEstaciones = { startActivity(Intent(this, EstacionesActivity::class.java)) },
                onNavigateRutas = { startActivity(Intent(this, RutasActivity::class.java)) },
                onNavigateConfiguracion = { startActivity(Intent(this, ConfiguracionActivity::class.java)) }
            )
        }
    }
}
