package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun AnalyticsTrendsScreenUI() {

    val waterLevels = listOf(3.2f, 3.5f, 4.0f, 4.8f, 4.6f, 5.1f, 5.5f)
    val avgLevel = waterLevels.average()
    val maxLevel = waterLevels.maxOrNull() ?: 0f
    val totalReadings = waterLevels.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .padding(16.dp)
    ) {
        Text(
            text = "Analytics & Trends",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Summary cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard(title = "Average Level", value = String.format("%.2f m", avgLevel))
            SummaryCard(title = "Max Recorded", value = String.format("%.2f m", maxLevel))
            SummaryCard(title = "Readings", value = totalReadings.toString())
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Line chart title
        Text(
            text = "Water Level Over Time",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Simple line chart
        LineChart(
            data = waterLevels,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(16.dp)
        )
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(110.dp)
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 13.sp, color = Color.Gray)
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1565C0)
            )
        }
    }
}

@Composable
fun LineChart(data: List<Float>, modifier: Modifier = Modifier) {
    if (data.isEmpty()) return

    Canvas(modifier = modifier) {
        val spacing = size.width / (data.size - 1)
        val maxY = (data.maxOrNull() ?: 1f) + 1f
        val minY = (data.minOrNull() ?: 0f) - 0.5f

        val path = Path()
        data.forEachIndexed { index, value ->
            val x = index * spacing
            val y = size.height - ((value - minY) / (maxY - minY)) * size.height
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        // Draw smooth blue line
        drawPath(
            path = path,
            color = Color(0xFF1565C0),
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )

        // Draw points
        data.forEachIndexed { index, value ->
            val x = index * spacing
            val y = size.height - ((value - minY) / (maxY - minY)) * size.height
            drawCircle(Color(0xFF1565C0), radius = 6f, center = Offset(x, y))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsTrendsScreenPreviewUI() {
    AnalyticsTrendsScreenUI()
}
