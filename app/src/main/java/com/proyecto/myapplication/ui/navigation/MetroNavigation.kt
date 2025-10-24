package com.proyecto.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.proyecto.myapplication.ui.screens.HomeScreenSimple
import com.proyecto.myapplication.ui.screens.EstacionesScreenSimple
import com.proyecto.myapplication.ui.screens.PlanificadorScreenSimple

@Composable
fun MetroNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreenSimple(
                onNavigateToEstaciones = {
                    navController.navigate("estaciones")
                },
                onNavigateToPlanificador = {
                    navController.navigate("planificador")
                }
            )
        }
        
        composable("estaciones") {
            EstacionesScreenSimple(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("planificador") {
            PlanificadorScreenSimple(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
