package com.jj.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jj.bookpedia.book.data.database.DatabaseFactory
import com.jj.bookpedia.book.data.database.FavoriteBookDatabase
import com.jj.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.jj.bookpedia.book.data.network.RemoteBookDataSource
import com.jj.bookpedia.book.data.repository.DefaultBookRepository
import com.jj.bookpedia.book.domain.BookRepository
import com.jj.bookpedia.book.presentation.SelectedBookViewModel
import com.jj.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.jj.bookpedia.book.presentation.book_list.BookListViewModel
import com.jj.bookpedia.camera.di.cameraModule
import com.jj.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModules = module {

    single {
        HttpClientFactory.create(get())
    }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single {
        get<FavoriteBookDatabase>().favoriteBookDao
    }


    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()
    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
    
    includes(cameraModule)
}