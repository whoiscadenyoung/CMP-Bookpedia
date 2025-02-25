package com.jj.bookpedia.camera.domain

import com.jj.bookpedia.camera.domain.model.CapturedImage
import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    suspend fun saveImage(bookId: String, imagePath: String): Result<CapturedImage, DataError>
    fun getImagesForBook(bookId: String): Flow<List<CapturedImage>>
    suspend fun deleteImage(imageId: Long): Result<Unit, DataError>
} 