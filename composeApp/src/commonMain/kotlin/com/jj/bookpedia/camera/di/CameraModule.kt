package com.jj.bookpedia.camera.di

import com.jj.bookpedia.camera.domain.BookDetector
import com.jj.bookpedia.camera.presentation.camera.CameraViewModel
import com.jj.bookpedia.camera.util.ImageFileManager
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cameraModule = module {
    // Provide BookDetector
    factoryOf(::BookDetectorProvider)
    factory<BookDetector> { get<BookDetectorProvider>().provideBookDetector() }
    
    // Provide ImageFileManager
    singleOf(::ImageFileManager)
    
    // Provide ViewModel
    viewModelOf(::CameraViewModel)
} 