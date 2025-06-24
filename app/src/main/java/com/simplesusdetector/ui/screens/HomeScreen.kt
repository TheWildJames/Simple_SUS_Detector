package com.simplesusdetector.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSusfsDetected: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var isDetecting by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple SUS Detector") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Simple SUS Detector",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            Text(
                text = "This app will check if you're using SUSFS",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )
            
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Start Detection", fontSize = 18.sp)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("SUSFS Detection") },
            text = { 
                Text("Are you using SUSFS?\n\nThis detection requires you to have SUSFS installed to work properly.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        isDetecting = true
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showDialog = false
                        // Close the app
                        (context as? android.app.Activity)?.finish()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    // Show detection progress
    if (isDetecting) {
        LaunchedEffect(isDetecting) {
            // Simulate fake detection process
            delay(2000) // 2 seconds of "detection"
            isDetecting = false
            onSusfsDetected()
        }
        
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Detecting...") },
            text = { 
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                    Text("Running SUSFS detection tests...")
                }
            },
            confirmButton = { }
        )
    }
}