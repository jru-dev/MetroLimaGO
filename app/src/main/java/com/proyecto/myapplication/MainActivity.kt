package com.proyecto.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.proyecto.myapplication.ui.navigation.MetroNavigation
import com.proyecto.myapplication.ui.theme.MetroLimaTheme
import com.proyecto.myapplication.ui.theme.ThemeManagerSingleton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MetroLimaApp()
                }
            }
        }
    }
}

@Composable
fun MetroLimaApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val themeManager = remember { ThemeManagerSingleton.getInstance(context) }
    val isDarkTheme by themeManager.isDarkTheme.collectAsState()
    val navController = rememberNavController()
    
    MetroLimaTheme(darkTheme = isDarkTheme) {
        MetroNavigation(navController = navController)
    }
}