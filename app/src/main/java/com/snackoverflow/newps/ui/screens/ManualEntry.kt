package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ManualReadingEntryScreenUI() {
    var waterLevel by remember { mutableStateOf("") }
    var siteName by remember { mutableStateOf("") }
    val timestamp = "12:34 PM"
    val gpsLocation = "28.6139, 77.209"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Manual Reading Entry",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                OutlinedTextField(
                    value = siteName,
                    onValueChange = { siteName = it },
                    label = { Text("Site") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = waterLevel,
                    onValueChange = { waterLevel = it },
                    label = { Text("Water Level Reading (m)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .background(Color(0xFFF1F8E9), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(text = "Timestamp: $timestamp", fontSize = 16.sp, color = Color.Black)
                    Text(text = "GPS: $gpsLocation", fontSize = 16.sp, color = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Photo & Map Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Gray),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Photo Preview", color = Color.White, fontWeight = FontWeight.Medium)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Map Preview", color = Color.Black, fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { /* TODO: Submit */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) { Text("Submit") }

            Button(
                onClick = { /* TODO: Retake */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) { Text("Retake") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManualReadingEntryPreviewUI() {
    ManualReadingEntryScreenUI()
}
