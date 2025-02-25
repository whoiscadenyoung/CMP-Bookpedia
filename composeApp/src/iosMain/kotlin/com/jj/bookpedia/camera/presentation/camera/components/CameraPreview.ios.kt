package com.jj.bookpedia.camera.presentation.camera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun CameraPreview(
    modifier: Modifier,
    onBookDetected: (Boolean) -> Unit,
    onImageCaptured: (String) -> Unit
) {
    // Stub implementation for iOS
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Camera not implemented for iOS yet",
            color = Color.White
        )
    }
} 