package com.snackoverflow.newps.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

data class SiteReading(
    val siteName: String,
    val waterLevel: String,
    val timestamp: String
import androidx.core.view.WindowCompat

data class Dam(
    val name: String,
    val river: String,
    val capacity: String,
    val change: String,
    val status: String
)

class SupervisorDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SupervisorDashboardScreenUI()
        }
    }
}

@Composable
fun SupervisorDashboardScreenUI(navController: NavController? = null) {
    val recentReadings = listOf(
        SiteReading("River Station 1", "3.5m", "12:34 PM"),
        SiteReading("River Station 2", "4.2m", "12:40 PM"),
        SiteReading("River Station 3", "5.1m", "12:50 PM")
fun SupervisorDashboardScreenUI() {

    val damList = listOf(
        Dam("Tehri Dam", "Bhagirathi River", "98.2% capacity", "+0.1%", "Critical"),
        Dam("Bhakra Dam", "Sutlej River", "95.6% capacity", "+0.1%", "Warning"),
        Dam("Sardar Sarovar Dam", "Narmada River", "90.9% capacity", "+0.1%", "Normal"),
        Dam("Hirakud Dam", "Mahanadi River", "98.2% capacity", "+0.2%", "Critical"),
        Dam("Nagarjuna Sagar Dam", "Krishna River", "97.3% capacity", "+0.1%", "Warning"),
        Dam("Idukki Dam", "Periyar River", "97.4% capacity", "+0.1%", "Normal"),
        Dam("Koyna Dam", "Koyna River", "98.1% capacity", "+0.2%", "Normal")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Top metric cards
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            MetricCard(
                title = "Total Sites Active",
                value = "12",
                valueColor = Color.Black,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Alerts Today",
                value = "3",
                valueColor = Color.Red,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Skipped Readings",
                value = "2",
                valueColor = Color.Black,
                modifier = Modifier.weight(1f)
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Dam Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Top Metrics Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MetricCard("Total Dams", "16", Color.Black, Modifier.weight(1f))
                MetricCard("Critical", "4", Color.Red, Modifier.weight(1f))
                MetricCard("Warning", "4", Color(0xFFFFA000), Modifier.weight(1f))
                MetricCard("Normal", "8", Color(0xFF388E3C), Modifier.weight(1f))
            }

        // Graph placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .horizontalScroll(enabled = true, state = rememberScrollState())
                    .fillMaxWidth()) {
                //Text("Graph Placeholder: Water Level Trends", color = Color.Gray)
                AlertsScreenUI()
            Spacer(modifier = Modifier.height(16.dp))

            // Mid Metrics Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MetricCard("Avg Level", "96.7%", Color(0xFF1565C0), Modifier.weight(1f))
                MetricCard("Inflow", "+12.5%", Color(0xFF388E3C), Modifier.weight(1f))
                MetricCard("Outflow", "-8.3%", Color.Red, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Environmental Metrics
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MetricCard("Rainfall (24h)", "45 mm", Color(0xFF1565C0), Modifier.weight(1f))
                MetricCard("Network Capacity", "97%", Color(0xFF2E7D32), Modifier.weight(1f))
                MetricCard("Total Capacity", "86%", Color(0xFF1565C0), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "All Dams - Live Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1565C0),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Dam Cards
        items(damList) { dam ->
            DamCard(dam)
        }
    }
}

@Composable
fun DamCard(dam: Dam) {
    val color = when (dam.status) {
        "Critical" -> Color(0xFFD32F2F)
        "Warning" -> Color(0xFFFFA000)
        else -> Color(0xFF388E3C)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(dam.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(dam.river, fontSize = 14.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Level: ${dam.capacity}", color = Color.Black, fontSize = 14.sp)
                Text("24h: ${dam.change}", color = Color.Gray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Status: ${dam.status}", color = color, fontWeight = FontWeight.SemiBold)
                Text("View →", color = Color(0xFF1565C0), fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 14.sp, color = Color.Gray)
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = valueColor)
        }
    }
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun SupervisorDashboardPreview() {
    SupervisorDashboardScreenUI()
}
