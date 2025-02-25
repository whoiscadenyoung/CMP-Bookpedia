package com.jj.bookpedia.camera.data.repository

import com.jj.bookpedia.camera.data.database.CapturedImageDao
import com.jj.bookpedia.camera.data.mappers.toCapturedImage
import com.jj.bookpedia.camera.data.mappers.toCapturedImageEntity
import com.jj.bookpedia.camera.domain.CameraRepository
import com.jj.bookpedia.camera.domain.model.CapturedImage
import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCameraRepository(
    private val capturedImageDao: CapturedImageDao
) : CameraRepository {
    
    override suspend fun saveImage(bookId: String, imagePath: String): Result<CapturedImage, DataError> {
        return try {
            val capturedImage = CapturedImage(
                bookId = bookId,
                imagePath = imagePath,
                captureDate = System.currentTimeMillis()
            )
            val id = capturedImageDao.insertImage(capturedImage.toCapturedImageEntity())
            Result.Success(capturedImage.copy(id = id))
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getImagesForBook(bookId: String): Flow<List<CapturedImage>> {
        return capturedImageDao.getImagesForBook(bookId).map { entities ->
            entities.map { it.toCapturedImage() }
        }
    }

    override suspend fun deleteImage(imageId: Long): Result<Unit, DataError> {
        return try {
            capturedImageDao.deleteImage(imageId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
} 