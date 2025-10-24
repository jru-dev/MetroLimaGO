# MetroLima GO 🚇

Una aplicación móvil para planificar viajes en el Metro de Lima, desarrollada con Android Jetpack Compose.

## 🎯 Características

- **Consulta de Estaciones**: Información detallada de todas las estaciones del Metro de Lima
- **Planificación de Rutas**: Calcula la mejor ruta entre estaciones con algoritmo inteligente
- **Búsqueda Inteligente**: Encuentra estaciones por nombre o línea con filtros avanzados
- **Rutas Favoritas**: Guarda y gestiona tus rutas preferidas
- **Alertas en Tiempo Real**: Información actualizada sobre retrasos y mantenimiento
- **Modo Oscuro/Claro**: Personaliza la apariencia de la aplicación
- **Interfaz Moderna**: Diseño con Material 3, animaciones y Jetpack Compose
- **Base de Datos Local**: Almacenamiento offline con Room
- **API Externa**: Integración con servicios del Metro de Lima

## 🛠️ Tecnologías Utilizadas

- **Android Jetpack Compose** - UI moderna y declarativa
- **Room** - Base de datos local
- **Retrofit** - Consumo de APIs REST
- **Hilt** - Inyección de dependencias
- **Navigation Compose** - Navegación entre pantallas
- **Coroutines** - Programación asíncrona
- **Material 3** - Sistema de diseño moderno

## 📱 Pantallas

### 1. Pantalla Principal (Home)
- Banner de bienvenida
- Acceso rápido a todas las funcionalidades
- Información del servicio del Metro

### 2. Lista de Estaciones
- Listado completo de estaciones
- Búsqueda por nombre
- Filtros por línea
- Información de accesibilidad

### 3. Detalle de Estación
- Información completa de la estación
- Horarios de operación
- Servicios disponibles
- Ubicación en el mapa

### 4. Planificador de Rutas
- Selección de origen y destino
- Cálculo de tiempo estimado
- Estaciones intermedias
- Rutas favoritas

### 5. Configuración
- Modo oscuro/claro
- Selección de idioma
- Notificaciones
- Información de la aplicación

## 🚀 Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/metrolima-go.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza las dependencias de Gradle

4. Ejecuta la aplicación en un dispositivo o emulador

## 📋 Requisitos

- Android Studio Arctic Fox o superior
- Android SDK 26 (Android 8.0) o superior
- Kotlin 1.8.0 o superior

## 🏗️ Estructura del Proyecto

```
app/
├── src/main/java/com/proyecto/myapplication/
│   ├── data/
│   │   ├── local/          # Room database, DAOs, entidades
│   │   ├── network/        # Retrofit, servicios de API
│   │   ├── model/          # Modelos de datos
│   │   └── repository/     # Repositorios
│   ├── ui/
│   │   ├── screens/        # Pantallas de la aplicación
│   │   ├── components/     # Componentes reutilizables
│   │   ├── navigation/     # Navegación entre pantallas
│   │   ├── viewmodel/      # ViewModels
│   │   └── theme/          # Temas y estilos
│   ├── di/                 # Módulos de Hilt
│   └── MainActivity.kt     # Actividad principal
```

## 🔧 Configuración

### Base de Datos
La aplicación utiliza Room para almacenar datos localmente. Los datos se inicializan automáticamente con información mock del Metro de Lima.

### API Externa
Configura la URL base de la API en `NetworkModule.kt`:
```kotlin
private const val BASE_URL = "https://tu-api.com/"
```

## 📊 Datos Mock

La aplicación incluye datos mock de 30 estaciones de la Línea 1 del Metro de Lima, incluyendo:
- Nombres de estaciones
- Líneas correspondientes
- Distritos
- Coordenadas geográficas
- Horarios de operación
- Servicios disponibles

## 🎨 Personalización

### Temas
La aplicación soporta modo claro y oscuro con colores personalizados del Metro de Lima.

### Idiomas
Soporte para español e inglés (configurable en la pantalla de configuración).

## 🚧 Próximas Funcionalidades

- [ ] Integración con mapas reales
- [ ] Notificaciones push
- [ ] Historial de viajes
- [ ] Calculadora de tarifas
- [ ] Información de tráfico en tiempo real
- [ ] Integración con transporte complementario

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autores

- **Tu Nombre** - *Desarrollo inicial* - [tu-github](https://github.com/tu-usuario)

## 🙏 Agradecimientos

- Metro de Lima por la información pública
- Comunidad de Android por las librerías open source
- Google por Jetpack Compose

---

**MetroLima GO** - Planifica tu viaje en el Metro de Lima de manera fácil y rápida! 🚇✨
