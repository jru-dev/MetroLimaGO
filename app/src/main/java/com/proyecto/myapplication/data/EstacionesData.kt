package com.proyecto.myapplication.data

data class Estacion(
    val id: String,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val color: String,
    val orden: Int
)

object EstacionesData {
    
    // Línea 1 - Metro de Lima (Rojo)
    val linea1 = listOf(
        Estacion("L1_01", "Villa El Salvador", "Línea 1", "Villa El Salvador", "#E30613", 1),
        Estacion("L1_02", "Parque Industrial", "Línea 1", "Villa El Salvador", "#E30613", 2),
        Estacion("L1_03", "Pumacahua", "Línea 1", "Villa El Salvador", "#E30613", 3),
        Estacion("L1_04", "Villa María", "Línea 1", "Villa María del Triunfo", "#E30613", 4),
        Estacion("L1_05", "María Auxiliadora", "Línea 1", "Villa María del Triunfo", "#E30613", 5),
        Estacion("L1_06", "San Juan", "Línea 1", "San Juan de Miraflores", "#E30613", 6),
        Estacion("L1_07", "Atocongo", "Línea 1", "San Juan de Miraflores", "#E30613", 7),
        Estacion("L1_08", "Jorge Chávez", "Línea 1", "Santiago de Surco", "#E30613", 8),
        Estacion("L1_09", "Ayacucho", "Línea 1", "Santiago de Surco", "#E30613", 9),
        Estacion("L1_10", "Cabitos", "Línea 1", "Santiago de Surco", "#E30613", 10),
        Estacion("L1_11", "Angamos", "Línea 1", "Santiago de Surco", "#E30613", 11),
        Estacion("L1_12", "San Borja Sur", "Línea 1", "San Borja", "#E30613", 12),
        Estacion("L1_13", "La Cultura", "Línea 1", "San Borja", "#E30613", 13),
        Estacion("L1_14", "Arriola", "Línea 1", "La Victoria", "#E30613", 14),
        Estacion("L1_15", "Gamarra", "Línea 1", "La Victoria", "#E30613", 15),
        Estacion("L1_16", "Miguel Grau", "Línea 1", "Cercado de Lima", "#E30613", 16),
        Estacion("L1_17", "El Ángel", "Línea 1", "Cercado de Lima", "#E30613", 17),
        Estacion("L1_18", "Presbítero Maestro", "Línea 1", "El Agustino", "#E30613", 18),
        Estacion("L1_19", "Caja de Agua", "Línea 1", "El Agustino", "#E30613", 19),
        Estacion("L1_20", "Pirámide del Sol", "Línea 1", "El Agustino", "#E30613", 20),
        Estacion("L1_21", "Los Jardines", "Línea 1", "San Juan de Lurigancho", "#E30613", 21),
        Estacion("L1_22", "Los Postes", "Línea 1", "San Juan de Lurigancho", "#E30613", 22),
        Estacion("L1_23", "San Carlos", "Línea 1", "San Juan de Lurigancho", "#E30613", 23),
        Estacion("L1_24", "San Martín", "Línea 1", "San Juan de Lurigancho", "#E30613", 24),
        Estacion("L1_25", "Santa Rosa", "Línea 1", "San Juan de Lurigancho", "#E30613", 25),
        Estacion("L1_26", "Bayóvar", "Línea 1", "San Juan de Lurigancho", "#E30613", 26)
    )
    
    // Línea 2 - Metro de Lima (Amarillo) - En construcción
    val linea2 = listOf(
        Estacion("L2_01", "Evitamiento", "Línea 2", "El Agustino", "#FFD700", 1),
        Estacion("L2_02", "Óvalo Santa Anita", "Línea 2", "Santa Anita", "#FFD700", 2),
        Estacion("L2_03", "Colectora Industrial", "Línea 2", "Santa Anita", "#FFD700", 3),
        Estacion("L2_04", "Hermilio Valdizán", "Línea 2", "Santa Anita", "#FFD700", 4),
        Estacion("L2_05", "Mercado Santa Anita", "Línea 2", "Santa Anita", "#FFD700", 5)
    )
    
    // Todas las estaciones
    val todasLasEstaciones = linea1 + linea2
    
    // Función para buscar estaciones
    fun buscarEstaciones(query: String): List<Estacion> {
        if (query.isBlank()) return todasLasEstaciones
        return todasLasEstaciones.filter { 
            it.nombre.contains(query, ignoreCase = true) ||
            it.distrito.contains(query, ignoreCase = true)
        }
    }
    
    // Función para obtener estaciones por línea
    fun getEstacionesPorLinea(linea: String): List<Estacion> {
        return when (linea) {
            "Línea 1" -> linea1
            "Línea 2" -> linea2
            else -> todasLasEstaciones
        }
    }
}
