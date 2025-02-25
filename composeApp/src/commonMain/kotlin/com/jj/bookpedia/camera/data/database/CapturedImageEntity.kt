package com.jj.bookpedia.camera.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CapturedImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: String,
    val imagePath: String,
    val captureDate: Long
) 