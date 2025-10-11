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
import androidx.navigation.NavController

data class DamAlert(
    val name: String,
    val river: String,
    val currentLevel: String,
    val change24h: String,
    val status: String
)

@Composable
fun AlertsScreenUI(navController: NavController? = null) {
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
fun AlertsScreenUI() {
    val damAlerts = listOf(
        DamAlert("Tehri Dam", "Bhagirathi River", "98.2% capacity", "+0.1%", "Critical"),
        DamAlert("Hirakud Dam", "Mahanadi River", "98.2% capacity", "+0.2%", "Critical"),
        DamAlert("Tehri Dam", "Bhagirathi River", "98.2% capacity", "+0.1%", "Critical"),
        DamAlert("Hirakud Dam", "Mahanadi River", "98.2% capacity", "+0.2%", "Critical")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFdbf4ff))
            .padding(16.dp)
    ) {
//        Text(
//            text = "Flagged Readings",
//            fontSize = 26.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF1565C0),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
        Text(
            text = "Major Dam Alerts",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0070c0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(damAlerts) { alert ->
                DamAlertCard(alert)
            }
        }
    }
}

@Composable
fun DamAlertCard(alert: DamAlert) {
    val statusColor = when (alert.status) {
        "Critical" -> Color(0xFFD32F2F)
        "Warning" -> Color(0xFFFFA000)
        else -> Color(0xFF388E3C)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFd8efff)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFd8efff))
                .padding(16.dp)
        ) {
            Text(alert.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0070c0))
            Text(alert.river, fontSize = 14.sp, color = Color(0xFF25a4ff))
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Current Level:", fontSize = 14.sp, color = Color.Gray)
                    Text(alert.currentLevel, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }

                Column {
                    Text("24H Change:", fontSize = 14.sp, color = Color.Gray)
                    Text(alert.change24h, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Status:", fontSize = 14.sp, color = Color.Gray)
                    Text(alert.status, fontWeight = FontWeight.SemiBold, color = statusColor, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* TODO: Navigate to detailed dam info */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25a4ff))
                ) {
                    Text("View →", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertsScreenPreviewUI() {
    AlertsScreenUI()
}
