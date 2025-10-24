package com.proyecto.myapplication.data.local

import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.data.model.Ruta
import com.proyecto.myapplication.data.model.Alerta
import com.proyecto.myapplication.data.model.TipoAlerta

object MockData {
    val estacionesMock = listOf(
        Estacion(
            id = "est_001",
            nombre = "Villa El Salvador",
            linea = "Línea 1",
            distrito = "Villa El Salvador",
            latitud = -12.2200,
            longitud = -76.9500,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_002",
            nombre = "Pumacahua",
            linea = "Línea 1",
            distrito = "Villa El Salvador",
            latitud = -12.2100,
            longitud = -76.9400,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_003",
            nombre = "Villa María del Triunfo",
            linea = "Línea 1",
            distrito = "Villa María del Triunfo",
            latitud = -12.2000,
            longitud = -76.9300,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_004",
            nombre = "Maria Auxiliadora",
            linea = "Línea 1",
            distrito = "Villa María del Triunfo",
            latitud = -12.1900,
            longitud = -76.9200,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_005",
            nombre = "San Juan",
            linea = "Línea 1",
            distrito = "San Juan de Miraflores",
            latitud = -12.1800,
            longitud = -76.9100,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_006",
            nombre = "Atocongo",
            linea = "Línea 1",
            distrito = "San Juan de Miraflores",
            latitud = -12.1700,
            longitud = -76.9000,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_007",
            nombre = "Jorge Chávez",
            linea = "Línea 1",
            distrito = "San Juan de Miraflores",
            latitud = -12.1600,
            longitud = -76.8900,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_008",
            nombre = "Ayacucho",
            linea = "Línea 1",
            distrito = "La Victoria",
            latitud = -12.1500,
            longitud = -76.8800,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_009",
            nombre = "Gamarra",
            linea = "Línea 1",
            distrito = "La Victoria",
            latitud = -12.1400,
            longitud = -76.8700,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_010",
            nombre = "El Ángel",
            linea = "Línea 1",
            distrito = "El Agustino",
            latitud = -12.1300,
            longitud = -76.8600,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_011",
            nombre = "Los Jardines",
            linea = "Línea 1",
            distrito = "El Agustino",
            latitud = -12.1200,
            longitud = -76.8500,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_012",
            nombre = "San Martín",
            linea = "Línea 1",
            distrito = "El Agustino",
            latitud = -12.1100,
            longitud = -76.8400,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_013",
            nombre = "La Cultura",
            linea = "Línea 1",
            distrito = "El Agustino",
            latitud = -12.1000,
            longitud = -76.8300,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_014",
            nombre = "Miguel Grau",
            linea = "Línea 1",
            distrito = "El Agustino",
            latitud = -12.0900,
            longitud = -76.8200,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_015",
            nombre = "Santa Anita",
            linea = "Línea 1",
            distrito = "Santa Anita",
            latitud = -12.0800,
            longitud = -76.8100,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_016",
            nombre = "Los Jazmines",
            linea = "Línea 1",
            distrito = "Santa Anita",
            latitud = -12.0700,
            longitud = -76.8000,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_017",
            nombre = "San Juan de Lurigancho",
            linea = "Línea 1",
            distrito = "San Juan de Lurigancho",
            latitud = -12.0600,
            longitud = -76.7900,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_018",
            nombre = "Caja de Agua",
            linea = "Línea 1",
            distrito = "San Juan de Lurigancho",
            latitud = -12.0500,
            longitud = -76.7800,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_019",
            nombre = "Bayóvar",
            linea = "Línea 1",
            distrito = "San Juan de Lurigancho",
            latitud = -12.0400,
            longitud = -76.7700,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_020",
            nombre = "Pirámide del Sol",
            linea = "Línea 1",
            distrito = "San Juan de Lurigancho",
            latitud = -12.0300,
            longitud = -76.7600,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_021",
            nombre = "Tecnológico",
            linea = "Línea 1",
            distrito = "San Juan de Lurigancho",
            latitud = -12.0200,
            longitud = -76.7500,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_022",
            nombre = "Plaza Norte",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -12.0100,
            longitud = -76.7400,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_023",
            nombre = "Naranjal",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -12.0000,
            longitud = -76.7300,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_024",
            nombre = "Tomás Valle",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9900,
            longitud = -76.7200,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_025",
            nombre = "El Milagro",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9800,
            longitud = -76.7100,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_026",
            nombre = "Independencia",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9700,
            longitud = -76.7000,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_027",
            nombre = "Pacífico",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9600,
            longitud = -76.6900,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_028",
            nombre = "Proceres",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9500,
            longitud = -76.6800,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        ),
        Estacion(
            id = "est_029",
            nombre = "Javier Prado",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9400,
            longitud = -76.6700,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = false,
            tieneBicicletero = false
        ),
        Estacion(
            id = "est_030",
            nombre = "La Cultura",
            linea = "Línea 1",
            distrito = "Independencia",
            latitud = -11.9300,
            longitud = -76.6600,
            horarioApertura = "05:00",
            horarioCierre = "23:00",
            tieneAcceso = true,
            tieneEstacionamiento = true,
            tieneBicicletero = true
        )
    )
    
    // Datos mock para rutas
    val rutasMock = listOf(
        Ruta(
            id = "ruta_001",
            estacionOrigen = "est_001", // Villa El Salvador
            estacionDestino = "est_030", // La Cultura
            tiempoEstimado = 45,
            estacionesIntermedias = listOf(
                "est_002", "est_003", "est_004", "est_005", "est_006",
                "est_007", "est_008", "est_009", "est_010", "est_011",
                "est_012", "est_013", "est_014", "est_015", "est_016",
                "est_017", "est_018", "est_019", "est_020", "est_021",
                "est_022", "est_023", "est_024", "est_025", "est_026",
                "est_027", "est_028", "est_029"
            ),
            esFavorita = true
        ),
        Ruta(
            id = "ruta_002",
            estacionOrigen = "est_010", // El Ángel
            estacionDestino = "est_020", // Pirámide del Sol
            tiempoEstimado = 25,
            estacionesIntermedias = listOf(
                "est_011", "est_012", "est_013", "est_014", "est_015",
                "est_016", "est_017", "est_018", "est_019"
            ),
            esFavorita = false
        ),
        Ruta(
            id = "ruta_003",
            estacionOrigen = "est_005", // San Juan
            estacionDestino = "est_025", // El Milagro
            tiempoEstimado = 35,
            estacionesIntermedias = listOf(
                "est_006", "est_007", "est_008", "est_009", "est_010",
                "est_011", "est_012", "est_013", "est_014", "est_015",
                "est_016", "est_017", "est_018", "est_019", "est_020",
                "est_021", "est_022", "est_023", "est_024"
            ),
            esFavorita = true
        )
    )
    
    // Datos mock para alertas
    val alertasMock = listOf(
        Alerta(
            id = "alerta_001",
            titulo = "Mantenimiento Programado",
            descripcion = "Se realizará mantenimiento preventivo en la Línea 1 entre las estaciones Villa El Salvador y San Juan",
            tipo = TipoAlerta.MANTENIMIENTO,
            estacionesAfectadas = listOf("est_001", "est_002", "est_003", "est_004", "est_005"),
            fechaInicio = System.currentTimeMillis() - 3600000, // 1 hora atrás
            fechaFin = System.currentTimeMillis() + 7200000 // 2 horas en el futuro
        ),
        Alerta(
            id = "alerta_002",
            titulo = "Retraso en el Servicio",
            descripcion = "Debido a la alta demanda, se presentan retrasos de 5-10 minutos en la Línea 1",
            tipo = TipoAlerta.RETRASO,
            estacionesAfectadas = listOf("est_001", "est_002", "est_003", "est_004", "est_005", "est_006", "est_007", "est_008", "est_009", "est_010"),
            fechaInicio = System.currentTimeMillis() - 1800000, // 30 minutos atrás
            fechaFin = System.currentTimeMillis() + 3600000 // 1 hora en el futuro
        ),
        Alerta(
            id = "alerta_003",
            titulo = "Información Importante",
            descripcion = "Recuerda que el horario de atención es de 5:00 AM a 11:00 PM. Gracias por usar el Metro de Lima",
            tipo = TipoAlerta.INFORMACION,
            estacionesAfectadas = emptyList(),
            fechaInicio = System.currentTimeMillis() - 86400000, // 1 día atrás
            fechaFin = System.currentTimeMillis() + 86400000 // 1 día en el futuro
        )
    )
}
