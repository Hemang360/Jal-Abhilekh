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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReadingDetailScreenUI() {
    var isApproved by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .padding(16.dp)
    ) {
        Text(
            text = "Reading Detail",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- Captured Image Card ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Captured Image Preview", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Reading Info Card ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Reading Value", color = Color.Gray, fontSize = 14.sp)
                Text("3.58 m", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Timestamp: 12:34 PM, 07 Oct 2025", fontSize = 14.sp, color = Color.DarkGray)
                Text("GPS: 28.6139, 77.2090", fontSize = 14.sp, color = Color.DarkGray)
                Text("Site: River Station 1", fontSize = 14.sp, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Small Map Placeholder ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Map Preview (GPS Location)", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Validation Badges ---
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ValidationBadge(text = "✅ Inside Geofence", bgColor = Color(0xFFDFF7E2), textColor = Color(0xFF2E7D32))
            if (showWarning) {
                ValidationBadge(text = "⚠️ EXIF Timestamp Mismatch", bgColor = Color(0xFFFFF4E0), textColor = Color(0xFFF57C00))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Action Buttons ---
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { showWarning = !showWarning },
                modifier = Modifier.weight(1f)
            ) {
                Text("Re-verify")
            }

            Button(
                onClick = { isApproved = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text(if (isApproved) "Approved ✅" else "Approve", color = Color.White)
            }
        }
    }
}

@Composable
fun ValidationBadge(text: String, bgColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = textColor, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingDetailPreviewUI() {
    ReadingDetailScreenUI()
}
