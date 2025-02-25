package com.jj.bookpedia.camera.presentation.camera.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun BookDetectionOverlay(
    modifier: Modifier = Modifier,
    isBookDetected: Boolean
) {
    val strokeColor = if (isBookDetected) Color.Green else Color.White
    val strokeWidth = 4f
    val cornerRadius = 20f
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // Calculate square size (80% of the smaller dimension)
        val squareSize = minOf(canvasWidth, canvasHeight) * 0.8f
        
        // Calculate top-left corner of the square
        val topLeft = Offset(
            (canvasWidth - squareSize) / 2f,
            (canvasHeight - squareSize) / 2f
        )
        
        // Draw rounded rectangle
        drawRoundRect(
            color = strokeColor,
            topLeft = topLeft,
            size = Size(squareSize, squareSize),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(
                width = strokeWidth,
                pathEffect = if (!isBookDetected) {
                    PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                } else null
            )
        )
    }
} 