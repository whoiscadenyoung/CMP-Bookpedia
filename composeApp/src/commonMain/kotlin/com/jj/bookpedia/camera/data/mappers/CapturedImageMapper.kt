package com.jj.bookpedia.camera.data.mappers

import com.jj.bookpedia.camera.data.database.CapturedImageEntity
import com.jj.bookpedia.camera.domain.model.CapturedImage

fun CapturedImageEntity.toCapturedImage(): CapturedImage {
    return CapturedImage(
        id = id,
        bookId = bookId,
        imagePath = imagePath,
        captureDate = captureDate
    )
}

fun CapturedImage.toCapturedImageEntity(): CapturedImageEntity {
    return CapturedImageEntity(
        id = id,
        bookId = bookId,
        imagePath = imagePath,
        captureDate = captureDate
    )
} 