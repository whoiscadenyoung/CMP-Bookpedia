package com.jj.bookpedia.camera.presentation.camera

sealed interface CameraAction {
    data object OnBackClick : CameraAction
    data object OnCaptureClick : CameraAction
    data class OnPermissionResult(val isGranted: Boolean) : CameraAction
    data class OnBookDetected(val isDetected: Boolean) : CameraAction
    data class OnImageCaptured(val imagePath: String) : CameraAction
    data object OnClearImage : CameraAction
} 