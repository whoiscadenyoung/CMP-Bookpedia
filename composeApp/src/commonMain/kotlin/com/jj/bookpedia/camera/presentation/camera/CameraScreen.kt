package com.jj.bookpedia.camera.presentation.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jj.bookpedia.camera.presentation.camera.components.BookDetectionOverlay
import com.jj.bookpedia.camera.presentation.camera.components.CameraControls
import com.jj.bookpedia.camera.presentation.camera.components.CameraPreview

@Composable
expect fun CameraPermissionHandler(
    onPermissionResult: (Boolean) -> Unit
)

@Composable
fun CameraScreenRoot(
    viewModel: CameraViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    CameraScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CameraAction.OnBackClick -> onBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun CameraScreen(
    state: CameraState,
    onAction: (CameraAction) -> Unit
) {
    // Handle camera permission
    CameraPermissionHandler { isGranted ->
        onAction(CameraAction.OnPermissionResult(isGranted))
    }
    
    // Track if we should capture an image
    var shouldCaptureImage by remember { mutableStateOf(false) }
    
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
        ) {
            if (state.hasCameraPermission) {
                // Camera preview
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onBookDetected = { isDetected ->
                        onAction(CameraAction.OnBookDetected(isDetected))
                    },
                    onImageCaptured = { imagePath ->
                        onAction(CameraAction.OnImageCaptured(imagePath))
                        // Reset capture flag
                        shouldCaptureImage = false
                    }
                )
                
                // Book detection overlay
                BookDetectionOverlay(
                    modifier = Modifier.fillMaxSize(),
                    isBookDetected = state.isBookDetected
                )
                
                // Camera controls
                CameraControls(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onBackClick = { onAction(CameraAction.OnBackClick) },
                    onCaptureClick = { 
                        if (state.isBookDetected) {
                            shouldCaptureImage = true
                            onAction(CameraAction.OnCaptureClick)
                        }
                    },
                    isBookDetected = state.isBookDetected
                )
            } else {
                // No camera permission
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Camera permission required",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
} 