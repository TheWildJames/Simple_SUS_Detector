package com.simplesusdetector.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.simplesusdetector.data.DetectionResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionScreen(
    onBackClick: () -> Unit
) {
    val detectionResults = getDetectionResults()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detection Results") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(detectionResults) { detection ->
                DetectionCard(detection = detection)
            }
        }
    }
}

@Composable
fun DetectionCard(detection: DetectionResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Warning",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = detection.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            
            if (detection.details.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                detection.details.forEach { detail ->
                    Text(
                        text = "• $detail",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                }
            }
            
            if (detection.fix.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fix:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Text(
                    text = detection.fix,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

fun getDetectionResults(): List<DetectionResult> {
    return listOf(
        DetectionResult(
            "Abnormal Environment",
            listOf("Modifications to system found", "Like root or some module")
        ),
        DetectionResult(
            "Conventional Tests (2)",
            listOf("Module found (?)")
        ),
        DetectionResult(
            "Conventional Tests (6)",
            listOf("Module found (?)", "(?)")
        ),
        DetectionResult(
            "Conventional Tests (8)",
            listOf("Unlocked bootloader")
        ),
        DetectionResult(
            "Conventional Tests (9)",
            listOf(
                "Unlocked bootloader",
                "ro.boot.flash.locked = 1 (?)",
                "ro.boot.verifiedbootstate = green (?)",
                "ro.secureboot.lockstate = locked (?)"
            )
        ),
        DetectionResult(
            "Conventional Tests (10)",
            listOf("Third party ROM")
        ),
        DetectionResult(
            "Conventional Tests (18)",
            listOf("Unlocked Bootloader", "Third Party ROM")
        ),
        DetectionResult(
            "Conventional Tests (1a)",
            listOf("(?)")
        ),
        DetectionResult(
            "Conventional Tests (1e)",
            listOf("Unlocked bootloader", "Third party ROM", "Magisk found")
        ),
        DetectionResult(
            "Conventional Tests (a)",
            listOf("Bootloader Spoofer hooks Native Test")
        ),
        DetectionResult(
            "Conventional Tests (e)",
            listOf("Unlocked Bootloader", "MIUI 12", "Magisk found", "RVX module (?)")
        ),
        DetectionResult(
            "Conventional Tests (f)",
            listOf("Unlocked Bootloader", "MIUI 12", "Magisk found")
        ),
        DetectionResult(
            "Evil Service (2)",
            listOf("LSPosed found")
        ),
        DetectionResult(
            "Evil Service (4)",
            listOf("Shamiko found (?)")
        ),
        DetectionResult(
            "Evil Service (6)",
            listOf("(?)")
        ),
        DetectionResult(
            "Found Hook",
            listOf("LSPosed module hooks Native Test")
        ),
        DetectionResult(
            "Found Injection",
            listOf("Zygisk found")
        ),
        DetectionResult(
            "Futile Hide (01)",
            listOf("(?)")
        ),
        DetectionResult(
            "Futile Hide (02)",
            listOf("Zygisk-Assistant on Kitsune (?)")
        ),
        DetectionResult(
            "Futile Hide (04)",
            listOf("Cherish Peakaboo KPM found")
        ),
        DetectionResult(
            "Futile Hide (08)",
            listOf("KSU Umount / zygisk-detach")
        ),
        DetectionResult(
            "Futile Hide (09)",
            listOf("(?)")
        ),
        DetectionResult(
            "Futile Hide (0a)",
            listOf("Magisk DenyList (?)")
        ),
        DetectionResult(
            "Futile Hide (0b)",
            listOf("(?)")
        ),
        DetectionResult(
            "Inconsistent Mount",
            listOf("Found module modifying /system/"),
            "Not use any modules that modify system files"
        ),
        DetectionResult(
            "Partition Modified",
            listOf("Partition Check Fail", "Invalid ro.boot.vbmeta.digest"),
            "Open Key Attestation App, scroll down and get the value of verifiedBootHash (screenshot and use Google lens to copy), then open Termux and run su -c resetprop -n ro.boot.vbmeta.digest [value you got]"
        ),
        DetectionResult(
            "Property Modified (01)",
            listOf("(?)")
        ),
        DetectionResult(
            "Property Modified (08)",
            listOf("(?)")
        ),
        DetectionResult(
            "Property Modified (10)",
            listOf("Executed resetprop --delete command"),
            "Remove the module or script that runs this command"
        ),
        DetectionResult(
            "Permission Loophole",
            listOf("Sepolicy not set correctly")
        ),
        DetectionResult(
            "Something wrong (999)",
            listOf("Internal app error")
        )
    )
}