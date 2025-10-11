package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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

data class SiteReadingDetail(
    val timestamp: String,
    val waterLevel: String,
    val officerName: String,
    val gps: String,
    val anomaly: Boolean = false
)

@Composable
fun DetailedSiteViewScreen(navController: NavController? = null) {
    val readings = listOf(
        SiteReadingDetail("12:00 PM", "3.5m", "Officer A", "28.6139,77.209"),
        SiteReadingDetail("12:30 PM", "4.0m", "Officer B", "28.6140,77.210", anomaly = true),
        SiteReadingDetail("1:00 PM", "4.2m", "Officer A", "28.6142,77.212")
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFE3F2FF))
        .padding(16.dp)
    ) {
        Text(text = "Detailed Site View", fontSize = 24.sp, color = Color(0xFF1565C0))
        Spacer(modifier = Modifier.height(16.dp))

        // Photo gallery placeholder (horizontal scroll)
        Row(modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
        ) {
            repeat(5) { index ->
                Box(modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Photo ${index + 1}", color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Table of readings
        Column(modifier = Modifier.fillMaxWidth()) {
            readings.forEach { reading ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.9f), shape = RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = reading.timestamp, modifier = Modifier.weight(1f))
                    Text(text = reading.waterLevel, modifier = Modifier.weight(1f))
                    Text(text = reading.officerName, modifier = Modifier.weight(1f))
                    Text(text = reading.gps, modifier = Modifier.weight(1f))
                    if (reading.anomaly) {
                        Text(text = "⚠️", color = Color.Red, modifier = Modifier.weight(0.5f))
                    } else {
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = { /* TODO: View on map */ }) { Text("View on Map") }
            Button(onClick = { /* TODO: Flag anomaly */ }) { Text("Flag Anomaly") }
        }

        // TODO: Add swipe gestures for photo timeline
        // TODO: Integrate AI-based anomaly detection icons dynamically
    }
}

@Preview(showBackground = true)
@Composable
fun DetailedSiteViewPreview() {
    DetailedSiteViewScreen()
}