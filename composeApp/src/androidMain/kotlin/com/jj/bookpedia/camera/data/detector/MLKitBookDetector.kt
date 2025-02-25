package com.jj.bookpedia.camera.data.detector

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.jj.bookpedia.camera.domain.BookDetector
import com.jj.bookpedia.core.domain.DataError
import com.jj.bookpedia.core.domain.Result
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MLKitBookDetector : BookDetector {
    private val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    
    override suspend fun detectBook(imageBytes: ByteArray): Result<Boolean, DataError> {
        return try {
            val image = InputImage.fromByteArray(
                imageBytes,
                /* width */ 480,
                /* height */ 360,
                0,
                InputImage.IMAGE_FORMAT_NV21
            )
            
            val result = processImage(image)
            // Simple heuristic: if we detect text with more than 10 characters, assume it's a book
            val isBook = result.text.length > 10
            Result.Success(isBook)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
    
    private suspend fun processImage(image: InputImage) = suspendCancellableCoroutine { continuation ->
        textRecognizer.process(image)
            .addOnSuccessListener { visionText ->
                continuation.resume(visionText)
            }
            .addOnFailureListener { e ->
                continuation.resume(com.google.mlkit.vision.text.Text("", emptyList()))
            }
    }
} 