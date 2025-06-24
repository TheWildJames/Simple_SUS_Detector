package com.simplesusdetector.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val EXPERIMENTAL_DETECTIONS_KEY = booleanPreferencesKey("experimental_detections")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onDetectionClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    val experimentalDetections by context.dataStore.data
        .map { preferences -> preferences[EXPERIMENTAL_DETECTIONS_KEY] ?: false }
        .collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Experimental Detections",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Enable additional root detection methods",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = experimentalDetections,
                            onCheckedChange = { enabled ->
                                scope.launch {
                                    context.dataStore.edit { preferences ->
                                        preferences[EXPERIMENTAL_DETECTIONS_KEY] = enabled
                                    }
                                }
                            }
                        )
                    }
                }
            }
            
            if (experimentalDetections) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDetectionClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Detection Results")
                }
            }
        }
    }
}