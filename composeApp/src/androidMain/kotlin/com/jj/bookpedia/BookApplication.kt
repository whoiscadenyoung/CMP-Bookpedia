package com.jj.bookpedia

import android.app.Application
import com.jj.bookpedia.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        initKoin{
            androidContext(this@BookApplication)
        }
    }
}
