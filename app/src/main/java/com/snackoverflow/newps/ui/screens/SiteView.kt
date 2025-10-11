package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

data class SiteReadingDetail(
    val damName: String,
    val timestamp: String,
    val waterLevel: String,
    val officerName: String,
    val gps: String,
    val anomaly: Boolean = false
)

@Composable
fun DetailedSiteViewScreen(navController: NavController? = null) {
    val readings = listOf(
        SiteReadingDetail("Tehri Dam", "12:00 PM", "97.3%", "Officer A", "30.4470,78.4070"),
        SiteReadingDetail("Tehri Dam", "12:30 PM", "97.4%", "Officer B", "30.4472,78.4075", anomaly = true),
        SiteReadingDetail("Hirakund Dam", "1:00 PM", "98.1%", "Officer C", "21.5975,83.7695")
    )

    // Color palette
    val lightBg = Color(0xFFdbf4ff)
    val cardBg = Color(0xFF9ce1ff)
    val primaryBlue = Color(0xFF25a4ff)
    val darkBlue = Color(0xFF0070c0)
    val white = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBg)
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Detailed Site View",
            fontSize = 26.sp,
            color = darkBlue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Horizontal photo gallery
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            readings.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(cardBg, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Photo ${index + 1}", color = white, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Table header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryBlue.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp))
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dam", modifier = Modifier.weight(1f), color = darkBlue, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Time", modifier = Modifier.weight(1f), color = darkBlue, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Level", modifier = Modifier.weight(1f), color = darkBlue, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Officer", modifier = Modifier.weight(1f), color = darkBlue, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("GPS", modifier = Modifier.weight(1f), color = darkBlue, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("", modifier = Modifier.weight(0.5f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Table rows
        Column(modifier = Modifier.fillMaxWidth()) {
            readings.forEach { reading ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(white, shape = RoundedCornerShape(6.dp))
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(reading.damName, modifier = Modifier.weight(1f), color = darkBlue)
                    Text(reading.timestamp, modifier = Modifier.weight(1f), color = darkBlue)
                    Text(reading.waterLevel, modifier = Modifier.weight(1f), color = darkBlue)
                    Text(reading.officerName, modifier = Modifier.weight(1f), color = darkBlue)
                    Text(reading.gps, modifier = Modifier.weight(1f), color = darkBlue)
                    if (reading.anomaly) {
                        Text("⚠️", color = Color.Red, modifier = Modifier.weight(0.5f))
                    } else {
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /* TODO: View on map */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) { Text("View on Map", color = white) }

            Button(
                onClick = { /* TODO: Flag anomaly */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) { Text("Flag Anomaly", color = white) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailedSiteViewPreview() {
    DetailedSiteViewScreen()
}
