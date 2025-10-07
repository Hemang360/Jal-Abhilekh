package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AlertReading(
    val site: String,
    val reading: String,
    val timestamp: String,
    val alertType: String, // e.g. "Outside Geofence", "Sudden Jump"
    val color: Color
)

@Composable
fun AlertsScreenUI() {
    val alertList = listOf(
        AlertReading(
            site = "River Station 1",
            reading = "3.98 m",
            timestamp = "07 Oct 2025, 11:42 AM",
            alertType = "Outside Geofence",
            color = Color(0xFFFFCDD2) // light red
        ),
        AlertReading(
            site = "River Station 2",
            reading = "6.12 m",
            timestamp = "07 Oct 2025, 10:58 AM",
            alertType = "Sudden Jump from Previous Reading",
            color = Color(0xFFFFF9C4) // light yellow
        ),
        AlertReading(
            site = "River Station 3",
            reading = "2.85 m",
            timestamp = "07 Oct 2025, 9:30 AM",
            alertType = "Duplicate Timestamp Detected",
            color = Color(0xFFFFE0B2) // light orange
        ),
        AlertReading(
            site = "Reservoir Gate A",
            reading = "4.25 m",
            timestamp = "07 Oct 2025, 8:15 AM",
            alertType = "Manual Correction Verified",
            color = Color(0xFFC8E6C9) // green
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .padding(16.dp)
    ) {
        Text(
            text = "Flagged Readings",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(alertList) { alert ->
                AlertCard(alert)
            }
        }
    }
}

@Composable
fun AlertCard(alert: AlertReading) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(alert.color.copy(alpha = 0.4f))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(alert.site, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                Text(alert.reading, fontSize = 16.sp, color = Color.DarkGray)
                Text(alert.timestamp, fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⚠ ${alert.alertType}",
                    color = Color(0xFFB71C1C),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = { /* TODO: add navigation to ReadingDetailScreenUI */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text("View", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertsScreenPreviewUI() {
    AlertsScreenUI()
}
