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
fun QRValidationScreenUI() {
    var qrResult by remember { mutableStateOf("") }
    var isVerified by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "QR Code Validation",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // QR Scanner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Scan QR Code",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Camera QR Scanner", color = Color.White, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = qrResult,
                    onValueChange = { qrResult = it },
                    label = { Text("QR Result") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        isVerified = qrResult == "S1" // example check
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Verify Site", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (isVerified) {
                    true -> Text(
                        text = "✔ Site Verified",
                        color = Color(0xFF2E7D32),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    false -> Text(
                        text = "Unauthorized site!",
                        color = Color(0xFFC62828),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    null -> {}
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Instructions Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Instructions",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "• Ensure you are scanning the correct QR code for the assigned site.\n" +
                            "• Verify before proceeding to capture readings.\n" +
                            "• If the QR code does not match, contact your supervisor.",
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 22.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QRValidationScreenPreview() {
    QRValidationScreenUI()
}
