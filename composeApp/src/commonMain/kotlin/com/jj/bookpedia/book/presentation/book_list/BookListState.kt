package com.jj.bookpedia.book.presentation.book_list

import com.jj.bookpedia.book.domain.Book
import com.jj.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery:String = "Kotlin",
    val isLoading:Boolean = true,
    val searchResults:List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val selectedTabIndex:Int = 0,
    val errorMessage:UiText?=null
)