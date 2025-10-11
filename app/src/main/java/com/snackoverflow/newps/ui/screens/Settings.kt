package com.snackoverflow.newps.ui.screens

import androidx.compose.foundation.background
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
import androidx.navigation.NavController

@Composable
fun SettingsProfileHelpScreen(navController: NavController? = null) {
    var isDarkMode by remember { mutableStateOf(false) }
    var syncFrequency by remember { mutableStateOf("15 min") }
    var language by remember { mutableStateOf("English") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFE3F2FF))
        .padding(16.dp)
    ) {
        Text(text = "Settings", fontSize = 28.sp, color = Color(0xFF1565C0), modifier = Modifier.padding(bottom = 16.dp))

        // Profile Info Block
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Profile", fontSize = 20.sp, color = Color(0xFF1565C0))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Name: John Doe", fontSize = 16.sp, color = Color.Black)
                Text(text = "Role: Field Staff", fontSize = 16.sp, color = Color.Black)
                Text(text = "Assigned Sites: River Station 1, River Station 2", fontSize = 16.sp, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Block
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Preferences", fontSize = 20.sp, color = Color(0xFF1565C0))
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Dark Mode", fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Sync Frequency", fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Text(text = syncFrequency, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Language", fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Button(onClick = { language = if (language == "English") "Hindi" else "English" }) {
                        Text(language)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Help Block
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Help", fontSize = 20.sp, color = Color(0xFF1565C0))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "How to Capture Gauge Image Properly:")
                // TODO: Add illustrations / tutorial content
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout button at the end
        Button(
            onClick = { /* TODO: Logout logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Logout", color = Color.White, fontSize = 16.sp)
        }

        // TODO: Add small water ripple background animation
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsProfileHelpPreview() {
    SettingsProfileHelpScreen()
}