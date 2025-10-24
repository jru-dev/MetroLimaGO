package com.proyecto.myapplication.di

import android.content.Context
import com.proyecto.myapplication.data.local.EstacionDao
import com.proyecto.myapplication.data.local.RutaDao
import com.proyecto.myapplication.data.local.ConfiguracionDao
import com.proyecto.myapplication.data.local.AlertaDao
import com.proyecto.myapplication.data.local.MetroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideMetroDatabase(@ApplicationContext context: Context): MetroDatabase {
        return MetroDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideEstacionDao(database: MetroDatabase): EstacionDao {
        return database.estacionDao()
    }
    
    @Provides
    fun provideRutaDao(database: MetroDatabase): RutaDao {
        return database.rutaDao()
    }
    
    @Provides
    fun provideConfiguracionDao(database: MetroDatabase): ConfiguracionDao {
        return database.configuracionDao()
    }
    
    @Provides
    fun provideAlertaDao(database: MetroDatabase): AlertaDao {
        return database.alertaDao()
    }
}
