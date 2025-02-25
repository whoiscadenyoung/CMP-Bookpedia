package com.jj.bookpedia.camera.presentation.camera

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jj.bookpedia.book.app.Route
import com.jj.bookpedia.camera.domain.CameraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel(
    private val cameraRepository: CameraRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val bookId = savedStateHandle.toRoute<Route.Camera>().bookId
    
    private val _state = MutableStateFlow(CameraState(bookId = bookId))
    val state = _state
        .onStart {
            loadCapturedImages()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)
    
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
            is CameraAction.OnDeleteImage -> {
                deleteImage(action.imageId)
            }
        }
    }
    
    private fun loadCapturedImages() {
        cameraRepository.getImagesForBook(bookId)
            .onEach { images ->
                _state.update { it.copy(capturedImages = images) }
            }
            .launchIn(viewModelScope)
    }
    
    private fun saveImage(imagePath: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            cameraRepository.saveImage(bookId, imagePath)
                .onSuccess {
                    _state.update { state -> state.copy(isLoading = false) }
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
    
    private fun deleteImage(imageId: Long) {
        viewModelScope.launch {
            cameraRepository.deleteImage(imageId)
        }
    }
} 