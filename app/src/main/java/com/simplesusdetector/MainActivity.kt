package com.simplesusdetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simplesusdetector.ui.screens.DetectionScreen
import com.simplesusdetector.ui.screens.HomeScreen
import com.simplesusdetector.ui.screens.SettingsScreen
import com.simplesusdetector.ui.screens.SusfsDetectedScreen
import com.simplesusdetector.ui.theme.SimpleSusDetectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleSusDetectorTheme {
                val navController = rememberNavController()
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                onSusfsDetected = { navController.navigate("susfs_detected") },
                                onSettingsClick = { navController.navigate("settings") }
                            )
                        }
                        composable("susfs_detected") {
                            SusfsDetectedScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBackClick = { navController.popBackStack() },
                                onDetectionClick = { navController.navigate("detection") }
                            )
                        }
                        composable("detection") {
                            DetectionScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}