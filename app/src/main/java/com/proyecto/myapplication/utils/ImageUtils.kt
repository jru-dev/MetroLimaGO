package com.proyecto.myapplication.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Utilidad para cargar imágenes de estaciones por su ID
 */
object ImageUtils {

    /**
     * Obtiene el ID del recurso drawable basado en el ID de la estación
     * @param context Contexto de la aplicación
     * @param estacionId ID de la estación (ej: "est_001")
     * @return ID del recurso drawable, o 0 si no existe
     */
    fun getEstacionImageResId(context: Context, estacionId: String): Int {
        // Convertir el ID a minúsculas para coincidir con el nombre del archivo
        val resName = estacionId.lowercase()

        // Buscar el recurso en drawable
        val resId = context.resources.getIdentifier(
            resName,      // Nombre del recurso (ej: "est_001")
            "drawable",   // Tipo de recurso
            context.packageName
        )

        return resId
    }

    /**
     * Verifica si existe una imagen para la estación
     * @param context Contexto de la aplicación
     * @param estacionId ID de la estación
     * @return true si existe la imagen, false en caso contrario
     */
    fun hasEstacionImage(context: Context, estacionId: String): Boolean {
        return getEstacionImageResId(context, estacionId) != 0
    }

    /**
     * Obtiene el ID del recurso con un nombre personalizado
     * Útil para otros tipos de imágenes en la app
     */
    fun getDrawableResId(context: Context, resourceName: String): Int {
        return context.resources.getIdentifier(
            resourceName.lowercase(),
            "drawable",
            context.packageName
        )
    }
}

/**
 * Extensión de Composable para obtener fácilmente el ID de imagen de una estación
 */
@Composable
fun getEstacionImageId(estacionId: String): Int {
    val context = LocalContext.current
    return ImageUtils.getEstacionImageResId(context, estacionId)
}

/**
 * Extensión de Composable para verificar si existe una imagen
 */
@Composable
fun hasEstacionImage(estacionId: String): Boolean {
    val context = LocalContext.current
    return ImageUtils.hasEstacionImage(context, estacionId)
}