package com.proyecto.myapplication.screens.datosExternos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.myapplication.repository.DatosExternosRepository
import com.proyecto.myapplication.utils.PreferenceHelper

class DatosExternosViewModelFactory(
    private val preferenceHelper: PreferenceHelper,
    private val repository: DatosExternosRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatosExternosViewModel::class.java)) {
            return DatosExternosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
