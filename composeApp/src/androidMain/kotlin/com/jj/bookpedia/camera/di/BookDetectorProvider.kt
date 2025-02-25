package com.jj.bookpedia.camera.di

import com.jj.bookpedia.camera.data.detector.MLKitBookDetector
import com.jj.bookpedia.camera.domain.BookDetector

actual class BookDetectorProvider {
    actual fun provideBookDetector(): BookDetector {
        return MLKitBookDetector()
    }
} 