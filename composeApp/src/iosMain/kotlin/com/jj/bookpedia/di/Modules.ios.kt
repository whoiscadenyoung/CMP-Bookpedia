package com.jj.bookpedia.di

import com.jj.bookpedia.book.data.database.DatabaseFactory
import io.ktor.client.engine.darwin.*
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { Darwin.create() }
        single { DatabaseFactory() }
    }