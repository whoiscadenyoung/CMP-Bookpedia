package com.jj.bookpedia.book.presentation

sealed interface BookDetailsEvents {

    data class Error(val message: String) : BookDetailsEvents
}