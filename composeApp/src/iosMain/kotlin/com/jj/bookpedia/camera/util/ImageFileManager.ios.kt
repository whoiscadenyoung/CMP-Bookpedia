package com.jj.bookpedia.camera.util

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import java.io.File

actual class ImageFileManager {
    actual fun getImagePath(bookId: String): String {
        val documentsDirectory = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String
        
        val directory = File(documentsDirectory, "book_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        
        return File(directory, "$bookId.jpg").absolutePath
    }
    
    actual fun deleteImage(bookId: String): Boolean {
        val file = File(getImagePath(bookId))
        return if (file.exists()) {
            NSFileManager.defaultManager.removeItemAtPath(file.absolutePath, null) ?: false
        } else {
            false
        }
    }
} 