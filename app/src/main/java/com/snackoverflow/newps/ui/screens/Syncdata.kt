package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OfflineReading(
    val siteName: String,
    val timestamp: String,
    val waterLevel: String,
    var status: String // "Pending", "Synced"
)

@Composable
fun OfflineQueueScreenEnhanced() {
    val scope = rememberCoroutineScope()
    var readings by remember {
        mutableStateOf(
            listOf(
                OfflineReading("Tehri Dam Station 1", "12:34 PM", "97.3%", "Pending"),
                OfflineReading("Hirakund Dam Station 2", "12:40 PM", "98.1%", "Pending"),
                OfflineReading("Tehri Dam Station 3", "12:50 PM", "97.4%", "Pending")
            )
        )
    }

    var isSyncing by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }

    // Colors
    val lightBg = Color(0xFFdbf4ff)
    val cardBg = Color(0xFFd8efff)
    val primaryBlue = Color(0xFF25a4ff)
    val darkBlue = Color(0xFF0070c0)
    val pendingColor = Color(0xFFFFA000)
    val syncedColor = Color(0xFF2E7D32)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Offline Queue",
                fontSize = 26.sp,
                color = darkBlue,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val pendingCount = readings.count { it.status == "Pending" }
            if (isSyncing) {
                Text(
                    text = "Syncing $pendingCount pending readings...",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.5f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = primaryBlue,
                    trackColor = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(readings) { reading ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(primaryBlue, shape = RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📷", color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(reading.siteName, fontSize = 16.sp, color = darkBlue)
                                Text("Water Level: ${reading.waterLevel}", fontSize = 14.sp, color = Color.DarkGray)
                                Text(reading.timestamp, fontSize = 12.sp, color = Color.Gray)
                            }

                            Text(
                                text = when (reading.status) {
                                    "Pending" -> "⏳"
                                    "Synced" -> "✔"
                                    else -> ""
                                },
                                fontSize = 18.sp,
                                color = when (reading.status) {
                                    "Pending" -> pendingColor
                                    "Synced" -> syncedColor
                                    else -> Color.Black
                                }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            if (reading.status == "Pending" && !isSyncing) {
                                Button(
                                    onClick = {
                                        readings = readings.map {
                                            if (it == reading) it.copy(status = "Synced") else it
                                        }
                                        showConfetti = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                                ) {
                                    Text("Retry", color = Color.White, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Sync All button
        FloatingActionButton(
            onClick = {
                isSyncing = true
                scope.launch {
                    readings = readings.map { it.copy(status = "Syncing") }
                    delay(2000) // simulate network sync
                    readings = readings.map { it.copy(status = "Synced") }
                    isSyncing = false
                    showConfetti = true
                }
            },
            containerColor = primaryBlue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Sync All", color = Color.White)
        }

        // Confetti placeholder
        if (showConfetti) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Text("🎉 SYNC COMPLETE! 🎉", fontSize = 24.sp, color = syncedColor)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OfflineQueueEnhancedPreview() {
    OfflineQueueScreenEnhanced()
}
