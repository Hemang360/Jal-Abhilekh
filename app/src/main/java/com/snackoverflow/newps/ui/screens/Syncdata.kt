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
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OfflineReading(
    val siteName: String,
    val timestamp: String,
    var status: String // "Pending", "Synced"
)

@Composable
fun OfflineQueueScreenEnhanced(navController: NavController? = null) {
    val scope = rememberCoroutineScope()
    var readings by remember {
        mutableStateOf(
            listOf(
                OfflineReading("River Station 1", "12:34 PM", "Pending"),
                OfflineReading("River Station 2", "12:40 PM", "Pending"),
                OfflineReading("River Station 3", "12:50 PM", "Pending")
            )
        )
    }

    var isSyncing by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFE3F2FF))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            Text(
                text = "Offline Queue",
                fontSize = 24.sp,
                color = Color(0xFF1565C0)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Background worker indicator
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
                    color = Color(0xFF1565C0),
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
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color.Gray, shape = RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Img", color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(reading.siteName, fontSize = 16.sp, color = Color.Black)
                                Text(reading.timestamp, fontSize = 12.sp, color = Color.DarkGray)
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = if (reading.status == "Pending") "⏳" else "✔",
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Retry button only
                            if (reading.status == "Pending" && !isSyncing) {
                                Button(onClick = {
                                    readings = readings.map {
                                        if (it == reading) it.copy(status = "Synced") else it
                                    }
                                    showConfetti = true
                                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))) {
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
            containerColor = Color(0xFF1565C0),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Sync All", color = Color.White)
        }

        // Confetti placeholder
        if (showConfetti) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Text("🎉 SYNC COMPLETE! 🎉", fontSize = 24.sp, color = Color(0xFF2E7D32))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OfflineQueueEnhancedPreview() {
    OfflineQueueScreenEnhanced()
}
