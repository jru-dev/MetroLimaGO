package com.proyecto.myapplication

import android.app.Application
import com.proyecto.myapplication.data.local.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MetroLimaApplication : Application() {
    
    @Inject
    lateinit var databaseInitializer: DatabaseInitializer
    
    override fun onCreate() {
        super.onCreate()
        
        // Inicializar base de datos en background
        CoroutineScope(Dispatchers.IO).launch {
            databaseInitializer.initializeDatabase()
        }
    }
}
