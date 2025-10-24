package com.proyecto.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MetroLimaApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}
