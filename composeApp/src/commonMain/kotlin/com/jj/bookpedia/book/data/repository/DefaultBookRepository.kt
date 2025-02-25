package com.jj.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.jj.bookpedia.book.data.database.FavoriteBookDao
import com.jj.bookpedia.book.data.mappers.toBook
import com.jj.bookpedia.book.data.mappers.toBookEntity
import com.jj.bookpedia.book.data.network.RemoteBookDataSource
import com.jj.bookpedia.book.domain.Book
import com.jj.bookpedia.book.domain.BookRepository
import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result
import com.jj.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {

        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {

        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if(localResult!=null){
            Result.Success(localResult.description)
        }else{
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        }

    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() } }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any{it.id == id}
            }
    }

    override suspend fun markAsFavorite(book: Book): Result<Unit, DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        }catch (e:SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }

    override suspend fun saveCustomImage(bookId: String, imagePath: String): Result<Unit, DataError.Local> {
        return try {
            favoriteBookDao.updateCustomImagePath(bookId, imagePath)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun clearCustomImage(bookId: String): Result<Unit, DataError.Local> {
        return try {
            favoriteBookDao.clearCustomImagePath(bookId)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}