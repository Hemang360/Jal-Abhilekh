package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.navigation.NavController

data class SiteReading(
    val siteName: String,
    val waterLevel: String,
    val timestamp: String
)

@Composable
fun SupervisorDashboardScreenUI(navController: NavController? = null) {
    val recentReadings = listOf(
        SiteReading("River Station 1", "3.5m", "12:34 PM"),
        SiteReading("River Station 2", "4.2m", "12:40 PM"),
        SiteReading("River Station 3", "5.1m", "12:50 PM")
    )

    Column(
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
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recent readings section
        Text(
            text = "Recent Readings",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(recentReadings) { reading ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color(0xFF90CAF9), shape = RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Img", color = Color.White, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = reading.siteName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Text(
                                text = "Water Level: ${reading.waterLevel}",
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                            Text(
                                text = reading.timestamp,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        // TODO: Integrate MPAndroidChart or Google Charts for smooth line graphs
        // TODO: Tap a site to open detailed photo + data + timestamp audit trail
    }
}

@Composable
fun MetricCard(title: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = valueColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SupervisorDashboardPreviewUI() {
    SupervisorDashboardScreenUI()
}
