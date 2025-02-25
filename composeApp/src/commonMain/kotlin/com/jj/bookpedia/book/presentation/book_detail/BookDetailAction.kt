package com.jj.bookpedia.book.presentation.book_detail

import com.jj.bookpedia.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
    data object OnCameraClick: BookDetailAction

    data class OnSelectedBookChange(val book:Book): BookDetailAction
}