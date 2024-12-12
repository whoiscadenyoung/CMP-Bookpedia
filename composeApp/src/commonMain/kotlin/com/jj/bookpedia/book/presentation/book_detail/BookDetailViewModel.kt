package com.jj.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jj.bookpedia.book.app.Route
import com.jj.bookpedia.book.domain.BookRepository
import com.jj.bookpedia.book.presentation.BookDetailsEvents
import com.jj.bookpedia.core.domain.onError
import com.jj.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val bookId = savedStateHandle.toRoute<Route.BookDetails>().id

    private var _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDescription()
            observeFavoriteStatus()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)

    private var _channel = Channel<BookDetailsEvents>()
    val channel = _channel.receiveAsFlow()


    private fun observeFavoriteStatus(){
        bookRepository
            .isBookFavorite(id = bookId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnFavoriteClick -> {
                if (state.value.isFavorite) {
                    deleteFavorite()
                } else {
                    markAsFavorite()
                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = action.book
                    )
                }
            }

            else -> Unit
        }
    }

    private fun deleteFavorite() {
        viewModelScope.launch {
            bookRepository.deleteFromFavorites(bookId)
        }
    }

    private fun markAsFavorite() {
        viewModelScope.launch {

            state.value.book?.let {

                bookRepository
                    .markAsFavorite(book = it)
                    .onError {

                    }
            }

        }
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            book = it.book?.copy(
                                description = description
                            )
                        )
                    }
                }
                .onError { error ->
                    println("Error:::${error}")
//                    _channel.send(BookDetailsEvents.Error(error))
                }

        }
    }

}