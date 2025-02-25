package com.jj.bookpedia.camera.presentation.camera

import com.jj.bookpedia.camera.domain.model.CapturedImage

data class CameraState(
    val bookId: String = "",
    val isLoading: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val isBookDetected: Boolean = false,
    val capturedImages: List<CapturedImage> = emptyList(),
    val error: String? = null
) 