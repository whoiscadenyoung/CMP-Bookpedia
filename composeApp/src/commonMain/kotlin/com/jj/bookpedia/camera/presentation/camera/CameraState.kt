package com.jj.bookpedia.camera.presentation.camera

data class CameraState(
    val bookId: String = "",
    val isLoading: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val isBookDetected: Boolean = false,
    val capturedImagePath: String? = null,
    val error: String? = null
) 