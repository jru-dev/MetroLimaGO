package com.proyecto.myapplication.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proyecto.myapplication.ui.screens.HomeScreenProfessional
import com.proyecto.myapplication.ui.screens.EstacionesScreenSimple
import com.proyecto.myapplication.ui.screens.PlanificadorScreenSimple
import com.proyecto.myapplication.ui.screens.InfoScreenSimple
import com.proyecto.myapplication.ui.screens.ConfiguracionScreen
import com.proyecto.myapplication.ui.screens.FavoritosScreen

@Composable
fun MetroNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            MetroBottomNavigation(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreenProfessional(
                    onNavigateToEstaciones = {
                        navController.navigate("estaciones")
                    },
                    onNavigateToPlanificador = {
                        navController.navigate("planificador")
                    },
                    onNavigateToFavoritos = {
                        navController.navigate("favoritos")
                    },
                    onNavigateToInfo = {
                        navController.navigate("info")
                    },
                    onNavigateToConfiguracion = {
                        navController.navigate("configuracion")
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


            composable("configuracion") {
                ConfiguracionScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("favoritos") {
                FavoritosScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToPlanificador = { origen, destino ->
                        // Navegar al planificador con las estaciones preseleccionadas
                        navController.navigate("planificador")
                    }
                )
            }
        }
    }
}
