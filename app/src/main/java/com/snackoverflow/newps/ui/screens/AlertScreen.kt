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
    // The list of dam alerts to be displayed.
    val damAlerts = listOf(
        DamAlert("Tehri Dam", "Bhagirathi River", "98.2% capacity", "+0.1%", "Critical"),
        DamAlert("Hirakud Dam", "Mahanadi River", "98.2% capacity", "+0.2%", "Critical"),
        DamAlert("Bhakra Dam", "Sutlej River", "92.5% capacity", "+0.15%", "Warning"),
        DamAlert("Nagarjuna Sagar", "Krishna River", "88.0% capacity", "+0.05%", "Normal")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFdbf4ff))
            .padding(16.dp)
    ) {
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
    // Determine the color based on the alert's status.
    val statusColor = when (alert.status) {
        "Critical" -> Color(0xFFD32F2F)
        "Warning" -> Color(0xFFFFA000)
        "Normal" -> Color(0xFF388E3C)
        else -> Color.Gray
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFd8efff)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(alert.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0070c0))
            Text(alert.river, fontSize = 14.sp, color = Color(0xFF25a4ff))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoColumn(title = "Current Level:", value = alert.currentLevel)
                InfoColumn(title = "24H Change:", value = alert.change24h)
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

// A helper composable to reduce code repetition in the card.
@Composable
private fun InfoColumn(title: String, value: String) {
    Column {
        Text(title, fontSize = 14.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun AlertsScreenPreviewUI() {
    AlertsScreenUI()
}