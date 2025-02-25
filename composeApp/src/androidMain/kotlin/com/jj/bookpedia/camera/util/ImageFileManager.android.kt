package com.jj.bookpedia.camera.util

import android.content.Context
import java.io.File

actual class ImageFileManager(
    private val context: Context
) {
    actual fun getImagePath(bookId: String): String {
        val directory = File(context.filesDir, "book_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return File(directory, "$bookId.jpg").absolutePath
    }
    
    actual fun deleteImage(bookId: String): Boolean {
        val file = File(getImagePath(bookId))
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }
} 