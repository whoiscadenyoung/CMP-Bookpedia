package com.jj.bookpedia

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jj.bookpedia.book.domain.Book
import com.jj.bookpedia.book.presentation.book_list.BookListScreen
import com.jj.bookpedia.book.presentation.book_list.BookListState
import com.jj.bookpedia.book.presentation.book_list.components.BookSearchBar

@Preview(showBackground = true)
@Composable
private fun BookSearchBarPreview() {
    MaterialTheme {
        BookSearchBar(
            modifier = Modifier.fillMaxWidth(),
            searchQuery = "Kotlin",
            onImeSearch = {},
            onSearchQueryChange = {}
        )
    }
}

val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("Author $it"),
        averageRating = 4.678,
        description = "Description $it",
        languages = emptyList(),
        ratingCount = 5,
        numPages = 100,
        numEditions = 3,
        firstPublishYear = null,
    )
}

@Preview
@Composable
private fun BookListScreenPreview() {
    MaterialTheme{
        BookListScreen(
            state = BookListState(
                searchResults = books
            ),
            onAction = {}
        )
    }
}