# Módulo "Datos Externos y Configuración" - MetroLimaGO

## Descripción
Este módulo implementa la funcionalidad de sincronización de datos externos y configuración de la aplicación MetroLimaGO.

## Características Implementadas

### 1. Datos Externos
- **ApiService.kt**: Interfaz para llamadas GET a JSON remoto (horarios.json)
- **RetrofitClient.kt**: Configuración de Retrofit con GsonConverter
- **DatosExternosRepository.kt**: Repositorio para obtener datos del API
- **DatosExternosViewModel.kt**: ViewModel con StateFlow para exponer datos
- **DatosExternosActivity.kt**: Actividad para mostrar mensajes y alertas

### 2. Configuración
- **ConfiguracionActivity.kt**: Actividad con opciones de configuración:
  - Cambiar modo claro/oscuro
  - Cambiar idioma (es/en)
  - Mostrar versión y créditos
  - Configurar notificaciones
  - Seleccionar tema (incluyendo tema Dragon Ball)

### 3. Persistencia
- **PreferenceHelper.kt**: Manejo de SharedPreferences y DataStore
- Almacenamiento de preferencias de usuario
- Sincronización de configuración

### 4. UI/UX
- **Material Design Components**
- **ViewBinding** habilitado
- **Tema Metro Lima** con colores oficiales del metro
- Layouts responsivos y modernos

## Tecnologías Utilizadas

- **Retrofit 2.9.0**: Para llamadas HTTP
- **Gson**: Para serialización JSON
- **Coroutines**: Para programación asíncrona
- **StateFlow**: Para manejo de estado
- **ViewBinding**: Para binding de vistas
- **Material Components**: Para UI moderna
- **DataStore**: Para preferencias modernas
- **Room**: Para base de datos local

## Estructura de Archivos

```
app/src/main/java/com/proyecto/myapplication/
├── data/
│   ├── model/
│   │   └── DatosExternos.kt
│   └── network/
│       ├── ApiService.kt
│       └── RetrofitClient.kt
├── repository/
│   └── DatosExternosRepository.kt
├── screens/
│   ├── datosExternos/
│   │   ├── DatosExternosActivity.kt
│   │   ├── DatosExternosViewModel.kt
│   │   └── DatosExternosViewModelFactory.kt
│   └── configuracion/
│       └── ConfiguracionActivity.kt
└── utils/
    └── PreferenceHelper.kt
```

## Layouts

- `activity_datos_externos.xml`: Interfaz para sincronización de datos
- `activity_configuracion.xml`: Interfaz de configuración
- `activity_main.xml`: Pantalla principal con navegación

## Archivos de Configuración

- `horarios.json`: Ejemplo de datos de horarios del metro
- `configuracion.json`: Configuración del servidor y temas

## Uso

### 1. Sincronización de Datos
```kotlin
// En DatosExternosActivity
viewModel.sincronizarDatos()
```

### 2. Configuración de Usuario
```kotlin
// Cambiar modo oscuro
preferenceHelper.setModoOscuro(true)

// Cambiar idioma
preferenceHelper.setIdioma("en")

// Cambiar tema
preferenceHelper.setTema("metro")
```

### 3. Observar Estado
```kotlin
// En el ViewModel
lifecycleScope.launch {
    viewModel.uiState.collect { state ->
        // Actualizar UI basado en el estado
    }
}
```

## Configuración del Servidor

Para usar datos reales, actualiza la URL base en `RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "https://tu-servidor.com/api/"
```

## Temas Disponibles

1. **Por defecto**: Colores estándar de Material Design
2. **Metro Lima**: Azul (#1E88E5) y amarillo (#FFC107) - colores oficiales
3. **Minimalista**: Grises (#424242, #757575)

## Permisos Requeridos

- `INTERNET`: Para llamadas HTTP
- `ACCESS_NETWORK_STATE`: Para verificar conectividad

## Dependencias Agregadas

```kotlin
// Retrofit y networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

// Material Components
implementation("com.google.android.material:material:1.11.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.0.0")
```

## Próximos Pasos

1. Implementar notificaciones push
2. Agregar más temas personalizados
3. Implementar sincronización automática
4. Agregar cache offline
5. Implementar analytics

## Notas de Desarrollo

- El código sigue las mejores prácticas de Android
- Usa arquitectura MVVM
- Implementa manejo de errores robusto
- Incluye logging para debugging
- Sigue las convenciones de Kotlin
