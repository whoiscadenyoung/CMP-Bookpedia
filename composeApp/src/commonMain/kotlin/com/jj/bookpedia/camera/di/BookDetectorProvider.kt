package com.jj.bookpedia.camera.di

import com.jj.bookpedia.camera.domain.BookDetector

expect class BookDetectorProvider() {
    fun provideBookDetector(): BookDetector
} 