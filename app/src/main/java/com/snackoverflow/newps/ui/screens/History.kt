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

data class ReadingRecord(
    val siteName: String,
    val value: String,
    val date: String,
    val isSynced: Boolean
)

@Composable
fun HistoryScreenUI(navController: NavController? = null) {
    var selectedFilter by remember { mutableStateOf("All") }

    // Sample data
    val readings = listOf(
        ReadingRecord("Site A", "3.72 m", "2025-10-06", true),
        ReadingRecord("Site B", "2.41 m", "2025-10-05", false),
        ReadingRecord("Site C", "4.02 m", "2025-10-04", true),
        ReadingRecord("Site D", "3.28 m", "2025-10-03", false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
    ) {
        // Simple top bar (no special TopAppBar APIs)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1565C0))
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = "My Submissions",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            // small textual filter hint on the right
            Text(
                text = "Filter",
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { /* optionally open a dialog */ }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Filter chips row (custom, stable implementation)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallFilterChip(label = "All", selected = selectedFilter == "All") { selectedFilter = it }
                SmallFilterChip(label = "Synced", selected = selectedFilter == "Synced") { selectedFilter = it }
                SmallFilterChip(label = "Pending", selected = selectedFilter == "Pending") { selectedFilter = it }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // List header
            Text(
                text = "Showing: ${selectedFilter.ifEmpty { "All" }}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Reading list (use weight so list gets remaining height)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                val filtered = readings.filter {
                    when (selectedFilter) {
                        "Synced" -> it.isSynced
                        "Pending" -> !it.isSynced
                        else -> true
                    }
                }

                items(filtered) { record ->
                    ReadingCardUI(record = record, onClick = { /* navigate to details */ })
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
        color = if (selected) Color(0xFF1565C0) else Color.White,
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
fun ReadingCardUI(record: ReadingRecord, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(record.siteName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Reading: ${record.value}", fontSize = 14.sp, color = Color.DarkGray)
                Text("Date: ${record.date}", fontSize = 13.sp, color = Color.Gray)
            }

            // status pill (text-only, no icon)
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .background(
                        color = if (record.isSynced) Color(0xFFDFF7E2) else Color(0xFFFFF4E0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (record.isSynced) "Synced" else "Pending",
                    color = if (record.isSynced) Color(0xFF2E7D32) else Color(0xFFF57C00),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryPreviewUI() {
    HistoryScreenUI()
}
