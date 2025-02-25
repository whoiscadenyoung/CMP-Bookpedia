package com.jj.bookpedia.camera.presentation.camera.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
expect fun CameraPreview(
    modifier: Modifier = Modifier,
    onBookDetected: (Boolean) -> Unit,
    onImageCaptured: (String) -> Unit
) 