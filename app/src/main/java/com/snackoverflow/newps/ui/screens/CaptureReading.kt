package com.snackoverflow.newps.ui.screens

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview as CameraXPreview // Aliased to resolve import conflict
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.snackoverflow.newps.ui.Screen
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Suppress("UNUSED_PARAMETER") // Suppress warning as navController might be used later
fun CaptureReadingScreen(navController: NavController? = null) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        // Permission is granted, show the main camera content
        CameraCaptureContent(navController)
    } else {
        // Permission not granted, show a request screen
        PermissionRequestContent(
            onGrantPermission = { cameraPermissionState.launchPermissionRequest() }
        )
    }
}

@Composable
private fun CameraCaptureContent(navController: NavController? = null) {
    var detectedNumber by remember { mutableStateOf("--") }
    var confidence by remember { mutableFloatStateOf(0f) } // Use primitive state for performance

    Box(modifier = Modifier.fillMaxSize()) {
        // Live camera feed as the background
        CameraPreview(modifier = Modifier.fillMaxSize())

        // Transparent overlay frame for aiming
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

        // Top UI: Detected number and confidence
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
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                // Use Locale for consistent string formatting across devices
                text = "Confidence: ${String.format(Locale.US, "%.2f", confidence * 100)}%",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        // Bottom UI: Action buttons and info
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
                Button(onClick = { navController?.navigate(Screen.SyncData.route) }, modifier = Modifier.weight(1f)) { Text("Capture") }
                Button(onClick = { /* TODO: Retake photo */ }, modifier = Modifier.weight(1f)) { Text("Retake") }
                Button(onClick = { /* TODO: Auto-detect OCR */ }, modifier = Modifier.weight(1f)) { Text("Auto-detect") }
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

@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Use AndroidView to embed the CameraX PreviewView into Compose
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            startCamera(context, lifecycleOwner, previewView.surfaceProvider, cameraSelector)
            previewView
        }
    )
}

private fun startCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    surfaceProvider: CameraXPreview.SurfaceProvider, // Use the aliased name
    cameraSelector: CameraSelector
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        try {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Build the Preview use case using the aliased CameraXPreview
            val preview = CameraXPreview.Builder().build().also {
                it.setSurfaceProvider(surfaceProvider)
            }

            // Unbind all previous use cases before rebinding
            cameraProvider.unbindAll()

            // Bind the preview use case to the camera and lifecycle
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview
            )
        } catch (exc: Exception) {
            Log.e("CaptureReadingScreen", "CameraX use case binding failed", exc)
        }
    }, ContextCompat.getMainExecutor(context))
}

@Composable
private fun PermissionRequestContent(onGrantPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Camera Permission Required",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "This feature requires camera access to capture readings.",
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onGrantPermission) {
            Text("Grant Permission")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CaptureReadingPreview() {
    // Preview the permission request screen as the camera cannot run in the preview window
    PermissionRequestContent(onGrantPermission = {})
}