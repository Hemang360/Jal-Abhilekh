package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReadingSummaryScreen(navController: NavController? = null) {
    // Dummy placeholders — replace with actual data
    val readingValue = "3.72 m"
    val siteName = "River Station 1"
    val timestamp = "12:45 PM"
    val gpsLocation = "28.6139, 77.209"
    val distance = "50m from site"
    val flaggedAnomaly = true // e.g., outside geofence

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6FB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reading Summary",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Photo Preview
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                // Placeholder for photo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Captured Photo Preview", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Reading Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Extracted Reading: $readingValue",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text("Site: $siteName", fontSize = 16.sp, color = Color.DarkGray)
                Text("Location: $gpsLocation", fontSize = 16.sp, color = Color.DarkGray)
                Text("Timestamp: $timestamp", fontSize = 16.sp, color = Color.DarkGray)
                Text("Distance: $distance", fontSize = 16.sp, color = Color.DarkGray)

                if (flaggedAnomaly) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFE0E0), shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "⚠ Outside geofence detected — verify before submission!",
                            color = Color(0xFFC62828),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { /* TODO: Go back to edit reading */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Edit", fontSize = 16.sp)
            }

            Button(
                onClick = { /* TODO: Submit reading */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text("Submit", fontSize = 16.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Footer note
        Text(
            text = "Review all data before submission.",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingSummaryPreview() {
    ReadingSummaryScreen()
}
