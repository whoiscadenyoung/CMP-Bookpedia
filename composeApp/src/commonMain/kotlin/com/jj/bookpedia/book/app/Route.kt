package com.jj.bookpedia.book.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object BookGraph: Route

    @Serializable
    data object BookList: Route

    @Serializable
    data class BookDetails(val id: String): Route
    
    @Serializable
    data class Camera(val bookId: String): Route
}