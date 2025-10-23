package com.proyecto.myapplication.data.network

import com.proyecto.myapplication.data.model.DatosExternos
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("datos-externos")
    suspend fun getDatosExternos(): Response<DatosExternos>
    
    @GET("estaciones")
    suspend fun getEstacionesRemotas(): Response<List<com.proyecto.myapplication.data.model.Estacion>>
}