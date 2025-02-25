package com.jj.bookpedia.camera.domain.model

data class CapturedImage(
    val id: Long = 0,
    val bookId: String,
    val imagePath: String,
    val captureDate: Long
) 