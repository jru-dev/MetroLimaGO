package com.proyecto.myapplication.data.network

import com.proyecto.myapplication.data.model.DatosExternos
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    
    @GET("horarios.json")
    suspend fun obtenerHorarios(): Response<DatosExternos>
    
    @GET("configuracion.json")
    suspend fun obtenerConfiguracion(): Response<Map<String, Any>>
}