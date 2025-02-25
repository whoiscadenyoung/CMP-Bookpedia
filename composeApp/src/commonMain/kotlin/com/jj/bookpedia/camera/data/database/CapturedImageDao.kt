package com.jj.bookpedia.camera.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CapturedImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: CapturedImageEntity): Long

    @Query("SELECT * FROM CapturedImageEntity WHERE bookId = :bookId ORDER BY captureDate DESC")
    fun getImagesForBook(bookId: String): Flow<List<CapturedImageEntity>>

    @Query("DELETE FROM CapturedImageEntity WHERE id = :imageId")
    suspend fun deleteImage(imageId: Long)
} 