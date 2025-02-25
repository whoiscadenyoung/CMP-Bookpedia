package com.jj.bookpedia.camera.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun CameraPermissionHandler(
    onPermissionResult: (Boolean) -> Unit
) {
    // Stub implementation for iOS
    // In a real implementation, we would use native iOS APIs to request camera permission
    LaunchedEffect(Unit) {
        // Always return false for now since we're not implementing iOS camera functionality
        onPermissionResult(false)
    }
} 