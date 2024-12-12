package com.jj.bookpedia.book.data.network

import com.jj.bookpedia.book.data.dto.BookWorkDto
import com.jj.bookpedia.book.data.dto.SearchResponseDto
import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result

 interface RemoteBookDataSource {

    suspend fun searchBooks(
        query: String,
        resultLimit: Int?=null,
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(
        bookWorkId:String
    ): Result<BookWorkDto, DataError.Remote>
}