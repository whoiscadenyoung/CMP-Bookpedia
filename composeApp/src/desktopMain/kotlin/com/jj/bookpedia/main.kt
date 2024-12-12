package com.jj.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jj.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Bookpedia",
        ) {
            App()
        }
    }
}