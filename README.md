# MetroLima GO 🚇

<div align="center">

![Version](https://img.shields.io/badge/Version-1.0.0-blue)
![Android](https://img.shields.io/badge/Platform-Android-green)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple)

**Aplicación móvil para planificar viajes en el Metro de Lima**

</div>

---

## 📱 Sobre el Proyecto

MetroLima GO es una aplicación Android moderna que ayuda a los usuarios a planificar sus viajes en el Metro de Lima. Desarrollada con Jetpack Compose y siguiendo las mejores prácticas de Android.

---

## ✨ Características

- 🚉 **Consulta de Estaciones** - Más de 30 estaciones con información completa
- 🗺️ **Planificador de Rutas** - Calcula la mejor ruta entre dos estaciones
- ⭐ **Rutas Favoritas** - Guarda tus rutas más usadas
- 🔔 **Alertas** - Información sobre retrasos y mantenimiento
- 🌙 **Modo Oscuro** - Tema claro y oscuro personalizados
- 🌐 **Multiidioma** - Soporte para Español e Inglés

---

## 🛠️ Tecnologías

- **Jetpack Compose** - UI moderna
- **Room** - Base de datos local
- **Retrofit** - Consumo de APIs
- **Hilt** - Inyección de dependencias
- **Coroutines** - Programación asíncrona
- **Material 3** - Diseño moderno

---

## 📋 Requisitos

- Android Studio Arctic Fox o superior
- Android SDK 26 (Android 8.0) o superior
- Kotlin 2.0.21 o superior

---

## 🚀 Instalación

1. Clona el repositorio
```bash
git clone https://github.com/tu-usuario/metrolima-go.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza las dependencias de Gradle

4. Ejecuta la aplicación en un dispositivo o emulador

---

## 📂 Estructura del Proyecto

```
app/
├── data/
│   ├── local/          # Base de datos Room
│   ├── network/        # API con Retrofit
│   ├── model/          # Modelos de datos
│   └── repository/     # Repositorios
│
├── ui/
│   ├── screens/        # Pantallas de la app
│   ├── components/     # Componentes reutilizables
│   ├── navigation/     # Navegación
│   ├── viewmodel/      # ViewModels
│   └── theme/          # Temas y colores
│
└── di/                 # Inyección de dependencias
```

---

## 🎨 Pantallas Principales

### 1. Inicio
- Acceso rápido a todas las funciones
- Banner de bienvenida
- Información del servicio

### 2. Estaciones
- Lista completa de estaciones
- Búsqueda por nombre o distrito
- Filtros por línea

### 3. Planificador
- Selecciona origen y destino
- Calcula ruta óptima
- Muestra tiempo estimado
- Guarda rutas favoritas

### 4. Favoritos
- Lista de rutas guardadas
- Acceso rápido para planificar
- Eliminar favoritos

### 5. Configuración
- Cambiar tema (claro/oscuro)
- Seleccionar idioma
- Información de la app

---

## 🗄️ Base de Datos

La app incluye **30 estaciones** de la Línea 1:

- Villa El Salvador → Bayóvar
- Distritos: Villa El Salvador, Villa María del Triunfo, San Juan de Miraflores, La Victoria, El Agustino, Santa Anita, San Juan de Lurigancho, Independencia

**5 estaciones** de la Línea 2 (en construcción):
- Evitamiento → Mercado Santa Anita

---

## 🎨 Personalización

### Temas
- **Tema Claro**: Amarillo dorado con azul navy
- **Tema Oscuro**: Azul navy oscuro con detalles amarillos

### Idiomas
- Español (por defecto)
- English

---

## 🔧 Funcionalidades Técnicas

### Sistema de Favoritos
- Almacenamiento local con SharedPreferences
- Máximo 10 rutas favoritas
- Sincronización en tiempo real

### Planificador de Rutas
- Algoritmo de búsqueda de ruta
- Cálculo de tiempo estimado (2 min por estación)
- Detección de transbordos entre líneas

### Base de Datos Local
- Room Database con 4 tablas
- Datos mock precargados
- TypeConverters para tipos complejos


---

## 👥 Equipo de Desarrollo

- **Alanya Leonardo** - Desarrollador
- **Alva Sebastian** - Desarrollador
- **Pasco Gianmarco** - Desarrollador
- **Azañero Vidal** - Desarrollador

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Haz fork del proyecto
2. Crea una rama (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---
