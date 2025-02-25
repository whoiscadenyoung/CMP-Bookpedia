package com.jj.bookpedia.camera.data.detector

import com.jj.bookpedia.camera.domain.BookDetector
import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result

class IOSBookDetector : BookDetector {
    override suspend fun detectBook(imageBytes: ByteArray): Result<Boolean, DataError> {
        // Stub implementation for iOS
        return Result.Success(true)
    }
} 