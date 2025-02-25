package com.jj.bookpedia.camera.presentation.camera

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jj.bookpedia.book.app.Route
import com.jj.bookpedia.book.domain.BookRepository
import com.jj.bookpedia.camera.util.ImageFileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel(
    private val bookRepository: BookRepository,
    private val imageFileManager: ImageFileManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val bookId = savedStateHandle.toRoute<Route.Camera>().bookId
    
    private val _state = MutableStateFlow(CameraState(bookId = bookId))
    val state = _state.stateIn(
        viewModelScope, 
        SharingStarted.WhileSubscribed(5_000L), 
        _state.value
    )
    
    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.OnBackClick -> {
                // Handled by navigation
            }
            is CameraAction.OnCaptureClick -> {
                // Handled by camera implementation
            }
            is CameraAction.OnPermissionResult -> {
                _state.update { it.copy(hasCameraPermission = action.isGranted) }
            }
            is CameraAction.OnBookDetected -> {
                _state.update { it.copy(isBookDetected = action.isDetected) }
            }
            is CameraAction.OnImageCaptured -> {
                saveImage(action.imagePath)
            }
            is CameraAction.OnClearImage -> {
                clearImage()
            }
        }
    }
    
    private fun saveImage(imagePath: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            // Save the image path in the database
            bookRepository.saveCustomImage(bookId, imagePath)
                .onSuccess {
                    _state.update { state -> 
                        state.copy(
                            isLoading = false,
                            capturedImagePath = imagePath
                        ) 
                    }
                }
                .onFailure { error ->
                    _state.update { state -> 
                        state.copy(
                            isLoading = false,
                            error = "Failed to save image: ${error.message}"
                        ) 
                    }
                }
        }
    }
    
    private fun clearImage() {
        viewModelScope.launch {
            val currentImagePath = state.value.capturedImagePath
            
            if (currentImagePath != null) {
                // Delete the image file
                imageFileManager.deleteImage(bookId)
                
                // Clear the image path in the database
                bookRepository.clearCustomImage(bookId)
                    .onSuccess {
                        _state.update { state -> 
                            state.copy(capturedImagePath = null) 
                        }
                    }
                    .onFailure { error ->
                        _state.update { state -> 
                            state.copy(
                                error = "Failed to clear image: ${error.message}"
                            ) 
                        }
                    }
            }
        }
    }
} 