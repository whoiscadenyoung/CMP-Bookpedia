package com.jj.bookpedia.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jj.bookpedia.camera.data.database.CapturedImageDao
import com.jj.bookpedia.camera.data.database.CapturedImageEntity

@Database(
    entities = [BookEntity::class, CapturedImageEntity::class],
    version = 2
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavoriteBookDatabase : RoomDatabase() {

    abstract val favoriteBookDao: FavoriteBookDao
    abstract val capturedImageDao: CapturedImageDao

    companion object {
        const val DB_NAME = "book.db"
    }
}