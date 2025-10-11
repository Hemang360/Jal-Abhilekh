package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController

data class DamRecord(
    val name: String,
    val river: String,
    val currentLevel: String,
    val change24h: String,
    val status: String
)

@Composable
fun HistoryScreenUI(navController: NavController? = null) {
fun DamHistoryScreenUI() {
    var selectedFilter by remember { mutableStateOf("All") }

    // Sample dam alert data
    val dams = listOf(
        DamRecord("Tehri Dam", "Bhagirathi River", "98.2% capacity", "+0.1%", "Critical"),
        DamRecord("Hirakud Dam", "Mahanadi River", "98.2% capacity", "+0.2%", "Critical"),
        DamRecord("Nagarjuna Sagar Dam", "Krishna River", "97.3% capacity", "+0.1%", "Warning"),
        DamRecord("Idukki Dam", "Periyar River", "97.4% capacity", "+0.1%", "Normal"),
        DamRecord("Koyna Dam", "Koyna River", "98.1% capacity", "+0.2%", "Normal")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFdbf4ff)) // normal bg
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0070c0)) // dark blue
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = "Dam History",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "Filter",
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { /* TODO: open filter options */ }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Filter chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallFilterChip(label = "All", selected = selectedFilter == "All") { selectedFilter = it }
                SmallFilterChip(label = "Critical", selected = selectedFilter == "Critical") { selectedFilter = it }
                SmallFilterChip(label = "Warning", selected = selectedFilter == "Warning") { selectedFilter = it }
                SmallFilterChip(label = "Normal", selected = selectedFilter == "Normal") { selectedFilter = it }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Showing: ${selectedFilter}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                val filtered = dams.filter {
                    when (selectedFilter) {
                        "Critical" -> it.status == "Critical"
                        "Warning" -> it.status == "Warning"
                        "Normal" -> it.status == "Normal"
                        else -> true
                    }
                }

                items(filtered) { dam ->
                    DamCardHistoryUI(dam)
                }
            }
        }
    }
}

@Composable
fun SmallFilterChip(label: String, selected: Boolean, onSelected: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp)
            .clickable { onSelected(label) },
        shape = RoundedCornerShape(18.dp),
        color = if (selected) Color(0xFF25a4ff) else Color.White,
        tonalElevation = if (selected) 4.dp else 0.dp
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 14.dp)) {
            Text(
                text = label,
                color = if (selected) Color.White else Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DamCardHistoryUI(dam: DamRecord) {
    val statusColor = when (dam.status) {
        "Critical" -> Color(0xFFD32F2F)
        "Warning" -> Color(0xFFFFA000)
        else -> Color(0xFF388E3C)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Navigate to detailed dam info */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFd8efff)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(dam.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0070c0))
            Text(dam.river, fontSize = 14.sp, color = Color(0xFF25a4ff))
            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Current Level:", fontSize = 14.sp, color = Color.Gray)
                    Text(dam.currentLevel, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }

                Column {
                    Text("24H Change:", fontSize = 14.sp, color = Color.Gray)
                    Text(dam.change24h, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Status:", fontSize = 14.sp, color = Color.Gray)
                    Text(dam.status, fontWeight = FontWeight.SemiBold, color = statusColor, fontSize = 14.sp)
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

@Preview(showBackground = true)
@Composable
fun DamHistoryPreviewUI() {
    DamHistoryScreenUI()
}
