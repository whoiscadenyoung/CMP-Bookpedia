package com.jj.bookpedia.camera.domain

import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result

interface BookDetector {
    suspend fun detectBook(imageBytes: ByteArray): Result<Boolean, DataError>
} 