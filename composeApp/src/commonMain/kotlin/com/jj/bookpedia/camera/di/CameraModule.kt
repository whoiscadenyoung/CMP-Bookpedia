package com.jj.bookpedia.camera.di

import com.jj.bookpedia.camera.data.repository.DefaultCameraRepository
import com.jj.bookpedia.camera.domain.BookDetector
import com.jj.bookpedia.camera.domain.CameraRepository
import com.jj.bookpedia.camera.presentation.camera.CameraViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cameraModule = module {
    // Provide BookDetector
    factoryOf(::BookDetectorProvider)
    factory<BookDetector> { get<BookDetectorProvider>().provideBookDetector() }
    
    // Provide CameraRepository
    single<CameraRepository> { 
        DefaultCameraRepository(
            capturedImageDao = get<com.jj.bookpedia.book.data.database.FavoriteBookDatabase>().capturedImageDao
        ) 
    }
    
    // Provide ViewModel
    viewModelOf(::CameraViewModel)
} 