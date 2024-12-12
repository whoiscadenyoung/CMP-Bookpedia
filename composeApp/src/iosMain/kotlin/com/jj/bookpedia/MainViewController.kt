package com.jj.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.jj.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }