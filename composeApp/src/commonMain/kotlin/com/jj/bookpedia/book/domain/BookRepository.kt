package com.jj.bookpedia.book.domain

import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.EmptyResult
import com.jj.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String):
            Result<List<Book>, DataError.Remote>

    suspend fun getBookDescription(bookId:String): Result<String?, DataError>

     fun getFavoriteBooks(): Flow<List<Book>>
     fun isBookFavorite(id:String) : Flow<Boolean>
     suspend fun markAsFavorite(book: Book): EmptyResult< DataError.Local>
     suspend fun deleteFromFavorites(id: String)
     
     suspend fun saveCustomImage(bookId: String, imagePath: String): EmptyResult<DataError.Local>
     suspend fun clearCustomImage(bookId: String): EmptyResult<DataError.Local>
}