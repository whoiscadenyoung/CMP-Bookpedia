package com.jj.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Upsert
    suspend fun upsert(book:BookEntity)

    @Query("Select * from BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity where id = :id")
    suspend fun getFavoriteBook(id:String): BookEntity?

    @Query("DELETE FROM BookEntity where id = :id")
    suspend fun deleteFavoriteBook(id:String)

    @Query("UPDATE BookEntity SET customImagePath = :imagePath WHERE id = :bookId")
    suspend fun updateCustomImagePath(bookId: String, imagePath: String)

    @Query("UPDATE BookEntity SET customImagePath = NULL WHERE id = :bookId")
    suspend fun clearCustomImagePath(bookId: String)
}