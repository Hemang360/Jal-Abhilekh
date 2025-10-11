package com.snackoverflow.newps.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.snackoverflow.newps.ui.Screen
import java.io.File

@Composable
fun showPlaceholder() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Tap to Open Camera",
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Scan QR Code",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )
    }
}

@Composable
fun QRValidationScreenUI(navController: NavController? = null) {
    var qrResult by remember { mutableStateOf("") }
    var isVerified by remember { mutableStateOf<Boolean?>(null) }
    // --- CHANGE 1: Add a state to track if an image has been captured ---
    var hasImage by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Create a temporary file for the photo
    // We declare it here so the cameraLauncher can access it.
    var photoFile by remember { mutableStateOf<File?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // Initialize file and URI only once
    LaunchedEffect(Unit) {
        try {
            val file = File.createTempFile(
                "qr_photo_${System.currentTimeMillis()}",
                ".jpg",
                context.cacheDir
            )
            photoFile = file
            photoUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error creating photo file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        // --- CHANGE 2: Update the 'hasImage' state on success ---
        if (success) {
            hasImage = true
            Toast.makeText(context, "Photo captured", Toast.LENGTH_SHORT).show()
        }
    }

    // Camera permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, now launch the camera
            photoUri?.let { uri ->
                cameraLauncher.launch(uri)
            } ?: Toast.makeText(context, "Photo file not ready", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }


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
                        .background(Color.Gray, shape = RoundedCornerShape(12.dp))
                        .clickable {
                            when {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED -> {
                                    photoUri?.let { uri ->
                                        cameraLauncher.launch(uri)
                                    } ?: Toast
                                        .makeText(context, "Photo file not ready", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else -> {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // --- CHANGE 3: Use 'hasImage' to decide what to display ---
                    if (hasImage && photoFile?.exists() == true) {
                        // Load the bitmap directly from the file path
                        val bitmap = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Captured QR Code",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Ensures the image fills the box
                        )
                    } else {
                        // Show placeholder if no image has been taken yet
                        showPlaceholder()
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = qrResult,
                    onValueChange = { qrResult = it },
                    label = { Text("QR Result (Manual Entry)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        navController?.navigate(Screen.Verification.route)
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
                        text = "✖ Unauthorized Site!",
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
                    text = "• Tap the grey box above to scan the site's QR code.\n" +
                            "• After scanning, manually enter the result if needed.\n" +
                            "• Tap 'Verify Site' to confirm your location.",
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