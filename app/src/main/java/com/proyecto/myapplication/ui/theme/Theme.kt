package com.proyecto.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MetroLightColorScheme = lightColorScheme(
    primary = MetroLightPrimary,
    onPrimary = MetroLightOnPrimary,
    primaryContainer = MetroLightPrimaryContainer,
    onPrimaryContainer = MetroLightOnPrimaryContainer,
    secondary = MetroLightSecondary,
    onSecondary = MetroLightOnSecondary,
    secondaryContainer = MetroLightSecondaryContainer,
    onSecondaryContainer = MetroLightOnSecondaryContainer,
    tertiary = MetroLightTertiary,
    onTertiary = MetroLightOnTertiary,
    tertiaryContainer = MetroLightTertiaryContainer,
    onTertiaryContainer = MetroLightOnTertiaryContainer,
    error = MetroLightError,
    onError = MetroLightOnError,
    errorContainer = MetroLightErrorContainer,
    onErrorContainer = MetroLightOnErrorContainer,
    background = MetroLightBackground,
    onBackground = MetroLightOnBackground,
    surface = MetroLightSurface,
    onSurface = MetroLightOnSurface,
    surfaceVariant = MetroLightSurfaceVariant,
    onSurfaceVariant = MetroLightOnSurfaceVariant,
    outline = MetroLightOutline,
    outlineVariant = MetroLightOutlineVariant
)

private val MetroDarkColorScheme = darkColorScheme(
    primary = MetroDarkPrimary,
    onPrimary = MetroDarkOnPrimary,
    primaryContainer = MetroDarkPrimaryContainer,
    onPrimaryContainer = MetroDarkOnPrimaryContainer,
    secondary = MetroDarkSecondary,
    onSecondary = MetroDarkOnSecondary,
    secondaryContainer = MetroDarkSecondaryContainer,
    onSecondaryContainer = MetroDarkOnSecondaryContainer,
    tertiary = MetroDarkTertiary,
    onTertiary = MetroDarkOnTertiary,
    tertiaryContainer = MetroDarkTertiaryContainer,
    onTertiaryContainer = MetroDarkOnTertiaryContainer,
    error = MetroDarkError,
    onError = MetroDarkOnError,
    errorContainer = MetroDarkErrorContainer,
    onErrorContainer = MetroDarkOnErrorContainer,
    background = MetroDarkBackground,
    onBackground = MetroDarkOnBackground,
    surface = MetroDarkSurface,
    onSurface = MetroDarkOnSurface,
    surfaceVariant = MetroDarkSurfaceVariant,
    onSurfaceVariant = MetroDarkOnSurfaceVariant,
    outline = MetroDarkOutline,
    outlineVariant = MetroDarkOutlineVariant
)

@Composable
fun MetroLimaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Deshabilitado para usar colores del Metro
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MetroDarkColorScheme
        else -> MetroLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Función para obtener el estado del tema
@Composable
fun rememberThemeState(): Boolean {
    return isSystemInDarkTheme()
}