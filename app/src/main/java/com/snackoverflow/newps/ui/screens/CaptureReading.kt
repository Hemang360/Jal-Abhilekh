package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CaptureReadingScreen() {

    var detectedNumber by remember { mutableStateOf("--") }
    var confidence by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Camera placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            // Transparent overlay frame
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.Center)
                    .border(
                        width = 3.dp,
                        color = Color.White.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }

        // Top: Detected number + confidence
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Detected: $detectedNumber",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Confidence: ${String.format("%.2f", confidence * 100)}%",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        // Bottom: Buttons + GPS + Timestamp
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* TODO: Capture photo */ },
                    modifier = Modifier.weight(1f)
                ) { Text("Capture") }

                Button(
                    onClick = { /* TODO: Retake photo */ },
                    modifier = Modifier.weight(1f)
                ) { Text("Retake") }

                Button(
                    onClick = { /* TODO: Auto-detect OCR */ },
                    modifier = Modifier.weight(1f)
                ) { Text("Auto-detect") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "GPS: 28.6139, 77.209 | Time: 12:34 PM",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaptureReadingPreview() {
    CaptureReadingScreen()
}
