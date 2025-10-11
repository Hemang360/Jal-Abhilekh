package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snackoverflow.newps.R
import com.snackoverflow.newps.ui.Screen
import kotlin.random.Random

@Composable
fun ReadingDetailScreenUI(navController: NavController? = null) {
    var isApproved by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(true) }

    // Analytics data (same as AnalyticsTrendsScreenUI)
    val waterLevels = listOf(3.2f, 3.5f, 4.0f, 4.8f, 4.6f, 5.1f, 5.5f)
    val avgLevel = 3.58
    val maxLevel = 3.64 ?: 0f
    val totalReadings = waterLevels.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Reading Detail",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- Analytics Summary Cards ---
        Text(
            text = "Analytics Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard(title = "Average Level", value = String.format("%.2f m", avgLevel))
            SummaryCard(title = "Max Recorded", value = String.format("%.2f m", maxLevel))
            SummaryCard(title = "Readings", value = totalReadings.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Line Chart ---
        Text(
            text = "Water Level Over Time",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LineChart(
            data = waterLevels,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Captured Image Card ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            // CHANGED: Replaced Box with Image
            Image(
                painter = painterResource(id = R.drawable.dam),
                contentDescription = "Captured image of the dam",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Fills the container, cropping if necessary
            )
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
            // CHANGED: Replaced Box with Image
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "Map preview of GPS location",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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
                onClick = { navController?.navigate(Screen.DashboardScreen.route) },
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