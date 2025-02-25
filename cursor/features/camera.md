# Book Photo Camera Feature

## Overview
This feature allows users to take photos of their favorite books. The camera functionality will be accessible from the book detail screen, but only for books that have been marked as favorites. The camera view will provide a square frame with rounded corners for users to align their books, and will automatically capture the image when a book is detected using ML Kit.

## Requirements
1. Add a camera button to the book detail screen (only visible for favorited books)
2. Create a camera screen with:
   - App background maintained
   - Square camera preview with rounded corners
   - Border overlay for book alignment
   - Book detection using ML Kit
   - Automatic photo capture when a book is detected
3. Store captured images in the database or file system
4. Properly handle Android camera permissions using Accompanist

## Architecture

### Package Structure
Following the project's clean architecture approach, we'll organize the camera feature as follows:

```
com.jj.bookpedia.camera/
├── domain/
│   ├── CameraRepository.kt (interface)
│   ├── BookDetector.kt (interface)
│   └── model/
│       └── CapturedImage.kt
├── data/
│   ├── repository/
│   │   └── DefaultCameraRepository.kt
│   ├── detector/
│   │   └── MLKitBookDetector.kt (Android-specific)
│   └── database/
│       ├── CapturedImageDao.kt
│       └── CapturedImageEntity.kt
└── presentation/
    ├── camera/
    │   ├── CameraAction.kt
    │   ├── CameraScreen.kt
    │   ├── CameraState.kt
    │   ├── CameraViewModel.kt
    │   └── components/
    │       ├── CameraPreview.kt
    │       ├── BookDetectionOverlay.kt
    │       └── CameraControls.kt
    └── book_detail/
        └── components/
            └── CameraButton.kt
```

### Implementation Details

#### Domain Layer
- `CameraRepository`: Interface defining camera operations
- `BookDetector`: Interface for book detection functionality
- `CapturedImage`: Domain model for captured images

#### Data Layer
- `DefaultCameraRepository`: Implementation of the CameraRepository interface
- `MLKitBookDetector`: Android-specific implementation of book detection using ML Kit
- Database entities and DAOs for storing captured images

#### Presentation Layer
- `CameraScreen`: UI for the camera view
- `CameraViewModel`: Manages camera state and actions
- `CameraButton`: UI component for the book detail screen

### Expected/Actual Pattern
We'll use the expected/actual pattern to handle platform-specific implementations:

- Common code will be in `commonMain`
- Android-specific implementations will be in `androidMain`
- iOS will have stub implementations in `iosMain`

## Database Schema Updates

We'll need to update the database schema to store captured images:

```kotlin
@Entity
data class CapturedImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: String,
    val imagePath: String,
    val captureDate: Long
)
```

## Dependencies
- Accompanist Permissions: For handling Android camera permissions
- CameraX: For camera functionality on Android
- ML Kit: For book detection
- Room: For database storage

## Implementation Plan

### Phase 1: Setup
1. Add required dependencies to build.gradle.kts
2. Update AndroidManifest.xml with camera permissions
3. Create domain layer interfaces and models

### Phase 2: Camera Integration
1. Implement CameraRepository and BookDetector
2. Create CameraScreen and ViewModel
3. Implement permission handling with Accompanist

### Phase 3: Book Detail Integration
1. Update BookDetailScreen to include camera button for favorited books
2. Connect camera button to navigation

### Phase 4: Storage Implementation
1. Update database schema to store captured images
2. Implement storage functionality in repository

### Phase 5: Testing and Refinement
1. Test on Android devices
2. Refine UI and UX
3. Optimize performance

## TODOs
- [x] Add camera dependencies to build.gradle.kts
- [x] Update AndroidManifest.xml with camera permissions
- [x] Create domain layer interfaces and models
- [x] Implement CameraRepository
- [x] Create CameraScreen and ViewModel
- [x] Implement permission handling with Accompanist
- [x] Update BookDetailScreen with camera button
- [x] Implement book detection with ML Kit
- [x] Update database schema for image storage
- [ ] Test and refine the feature

## Implementation Progress
- ✅ Added camera dependencies (CameraX, ML Kit, Accompanist Permissions)
- ✅ Updated AndroidManifest.xml with camera permissions
- ✅ Created domain layer interfaces (CameraRepository, BookDetector)
- ✅ Created data layer with Room database integration
- ✅ Implemented DefaultCameraRepository
- ✅ Created Android-specific ML Kit book detector
- ✅ Added iOS stub implementations
- ✅ Created camera UI components (CameraPreview, BookDetectionOverlay, CameraControls)
- ✅ Implemented permission handling with Accompanist
- ✅ Added camera button to BookDetailScreen (only for favorited books)
- ✅ Updated navigation to include camera screen
- ✅ Implemented database migration to add CapturedImageEntity
- ✅ Set up dependency injection with Koin

## Next Steps
- Test the camera functionality on Android devices
- Refine the book detection algorithm
- Add image gallery view to show captured images
- Implement proper error handling
- Add unit and integration tests
- Optimize camera performance

## Navigation
We'll need to update the navigation to include the camera screen:

```kotlin
// In Route.kt
sealed class Route(val route: String) {
    // Existing routes...
    object Camera : Route("camera/{bookId}") {
        fun createRoute(bookId: String) = "camera/$bookId"
    }
}

// In App.kt
NavHost(/*...*/) {
    // Existing routes...
    composable(
        route = Route.Camera.route,
        arguments = listOf(navArgument("bookId") { type = NavType.StringType })
    ) { backStackEntry ->
        val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
        CameraScreen(
            bookId = bookId,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
```

## Permissions
We'll use Accompanist Permissions to handle camera permissions:

```kotlin
val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

LaunchedEffect(Unit) {
    permissionState.launchPermissionRequest()
}

when {
    permissionState.status.isGranted -> {
        // Show camera UI
    }
    permissionState.status.shouldShowRationale -> {
        // Show rationale UI
    }
    else -> {
        // Show permission denied UI
    }
}
```

## Notes
- The camera feature will initially focus on Android implementation
- iOS implementation will be minimal with stub functions
- We'll need to carefully handle the lifecycle of the camera to avoid resource leaks
- Book detection may require tuning for optimal performance 