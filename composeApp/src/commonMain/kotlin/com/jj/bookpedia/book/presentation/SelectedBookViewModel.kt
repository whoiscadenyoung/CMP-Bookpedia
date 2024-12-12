package com.jj.bookpedia.book.presentation

import androidx.lifecycle.ViewModel
import com.jj.bookpedia.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedBookViewModel  : ViewModel(){

    private var _selectedBook=  MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun setSelectedBook(book: Book?){
        _selectedBook.value = book
    }
}
