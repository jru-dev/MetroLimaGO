package com.proyecto.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyecto.myapplication.ui.components.EstacionCard
import com.proyecto.myapplication.ui.components.FilterChip
import com.proyecto.myapplication.ui.viewmodel.EstacionesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetalle: (String) -> Unit,
    viewModel: EstacionesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedLinea by remember { mutableStateOf("Todas") }
    
    val lineas = listOf("Todas", "Línea 1", "Línea 2", "Línea 3")
    
    LaunchedEffect(searchQuery, selectedLinea) {
        if (selectedLinea == "Todas") {
            viewModel.searchEstaciones(searchQuery)
        } else {
            viewModel.filterByLinea(selectedLinea)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estaciones") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar estación...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    Icon(Icons.Filled.Info, contentDescription = "Filtros")
                },
                singleLine = true
            )
            
            // Filtros por línea
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(lineas) { linea ->
                    FilterChip(
                        selected = selectedLinea == linea,
                        onClick = { selectedLinea = linea },
                        label = { Text(linea) }
                    )
                }
            }
            
            // Lista de estaciones
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.estaciones) { estacion ->
                    EstacionCard(
                        estacion = estacion,
                        onClick = { onNavigateToDetalle(estacion.id) }
                    )
                }
            }
        }
    }
}
