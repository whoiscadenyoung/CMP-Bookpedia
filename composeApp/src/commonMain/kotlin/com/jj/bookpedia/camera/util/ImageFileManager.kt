package com.jj.bookpedia.camera.util

import java.io.File

/**
 * Utility class for managing book image files.
 * This class provides methods for getting image paths and deleting images.
 */
expect class ImageFileManager() {
    /**
     * Gets the absolute path for a book image file.
     * @param bookId The ID of the book
     * @return The absolute path to the image file
     */
    fun getImagePath(bookId: String): String
    
    /**
     * Deletes the image file for a book.
     * @param bookId The ID of the book
     * @return True if the file was deleted successfully, false otherwise
     */
    fun deleteImage(bookId: String): Boolean
} 