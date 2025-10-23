package com.proyecto.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.proyecto.myapplication.ui.screens.HomeScreen
import com.proyecto.myapplication.ui.screens.ListaEstacionesScreen
import com.proyecto.myapplication.ui.screens.RutasScreen
import com.proyecto.myapplication.ui.screens.DetalleEstacionScreen
import com.proyecto.myapplication.ui.screens.ConfiguracionScreen
import com.proyecto.myapplication.ui.screens.AlertasScreen
import com.proyecto.myapplication.ui.screens.PlanificadorRutasScreen

@Composable
fun MetroNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToEstaciones = {
                    navController.navigate("estaciones")
                },
                onNavigateToRutas = {
                    navController.navigate("rutas")
                },
                onNavigateToConfiguracion = {
                    navController.navigate("configuracion")
                }
            )
        }
        
        composable("estaciones") {
            ListaEstacionesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetalle = { estacionId ->
                    navController.navigate("detalle_estacion/$estacionId")
                }
            )
        }
        
        composable("rutas") {
            RutasScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("planificador") {
            PlanificadorRutasScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("alertas") {
            AlertasScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("configuracion") {
            ConfiguracionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("detalle_estacion/{estacionId}") { backStackEntry ->
            val estacionId = backStackEntry.arguments?.getString("estacionId") ?: ""
            DetalleEstacionScreen(
                estacionId = estacionId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
