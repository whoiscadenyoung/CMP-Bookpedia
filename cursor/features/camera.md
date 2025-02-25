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
3. Store captured image path in the favorite book entity
4. Properly handle Android camera permissions using Accompanist

## Architecture

### Package Structure
Following the project's clean architecture approach, we've simplified the camera feature as follows:

```
com.jj.bookpedia/
├── book/
│   ├── domain/
│   │   ├── BookRepository.kt (interface, updated with image methods)
│   │   └── model/
│   │       └── Book.kt (updated with customImagePath)
│   ├── data/
│   │   ├── repository/
│   │   │   └── DefaultBookRepository.kt (updated with image methods)
│   │   └── database/
│   │       ├── FavoriteBookDao.kt (updated with image methods)
│   │       └── BookEntity.kt (updated with customImagePath)
└── camera/
    ├── domain/
    │   └── BookDetector.kt (interface)
    ├── data/
    │   └── detector/
    │       └── MLKitBookDetector.kt (Android-specific)
    ├── presentation/
    │   ├── camera/
    │   │   ├── CameraAction.kt
    │   │   ├── CameraScreen.kt
    │   │   ├── CameraState.kt
    │   │   ├── CameraViewModel.kt
    │   │   └── components/
    │   │       ├── CameraPreview.kt
    │   │       ├── BookDetectionOverlay.kt
    │   │       └── CameraControls.kt
    │   └── book_detail/
    │       └── components/
    │           └── CameraButton.kt
    └── util/
        └── ImageFileManager.kt (platform-specific file management)
```

### Implementation Details

#### Domain Layer
- `BookRepository`: Interface updated with methods for saving and clearing custom images
- `Book`: Domain model updated with customImagePath field
- `BookDetector`: Interface for book detection functionality

#### Data Layer
- `DefaultBookRepository`: Implementation updated with methods for saving and clearing custom images
- `FavoriteBookDao`: Updated with methods for updating and clearing the custom image path
- `BookEntity`: Updated with customImagePath field

#### Presentation Layer
- `CameraScreen`: UI for the camera view
- `CameraViewModel`: Manages camera state and actions, now using BookRepository
- `CameraButton`: UI component for the book detail screen
- `ImageFileManager`: Platform-specific utility for managing image files

### Expected/Actual Pattern
We use the expected/actual pattern to handle platform-specific implementations:

- Common code is in `commonMain`
- Android-specific implementations are in `androidMain`
- iOS has stub implementations in `iosMain`

## Database Schema Updates

We've simplified the database schema by adding a customImagePath field to the BookEntity:

```kotlin
@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val languages: List<String>,
    val authors: List<String>,
    val firstPublishYear: String?,
    val ratingsAverage: Double?,
    val ratingsCount: Int?,
    val numPagesMedian: Int?,
    val numEditions: Int,
    val customImagePath: String? = null
)
```

## Dependencies
- Accompanist Permissions: For handling Android camera permissions
- CameraX: For camera functionality on Android
- ML Kit: For book detection

## Implementation Plan

### Phase 1: Setup
1. Add required dependencies to build.gradle.kts
2. Update AndroidManifest.xml with camera permissions
3. Update domain layer interfaces and models with image support

### Phase 2: Camera Integration
1. Implement BookDetector
2. Create CameraScreen and ViewModel
3. Implement permission handling with Accompanist

### Phase 3: Book Detail Integration
1. Update BookDetailScreen to include camera button for favorited books
2. Connect camera button to navigation

### Phase 4: Storage Implementation
1. Update BookEntity with customImagePath field
2. Implement storage functionality in BookRepository

### Phase 5: Testing and Refinement
1. Test on Android devices
2. Refine UI and UX
3. Optimize performance

## TODOs
- [x] Add camera dependencies to build.gradle.kts
- [x] Update AndroidManifest.xml with camera permissions
- [x] Update domain layer interfaces and models with image support
- [x] Update BookRepository with image methods
- [x] Create CameraScreen and ViewModel
- [x] Implement permission handling with Accompanist
- [x] Update BookDetailScreen with camera button
- [x] Implement book detection with ML Kit
- [x] Create ImageFileManager for platform-specific file handling
- [ ] Test and refine the feature

## Implementation Progress
- ✅ Added camera dependencies (CameraX, ML Kit, Accompanist Permissions)
- ✅ Updated AndroidManifest.xml with camera permissions
- ✅ Updated domain layer interfaces (Book, BookRepository)
- ✅ Updated data layer (BookEntity, FavoriteBookDao)
- ✅ Implemented image methods in DefaultBookRepository
- ✅ Created Android-specific ML Kit book detector
- ✅ Added iOS stub implementations
- ✅ Created camera UI components (CameraPreview, BookDetectionOverlay, CameraControls)
- ✅ Implemented permission handling with Accompanist
- ✅ Added camera button to BookDetailScreen (only for favorited books)
- ✅ Updated navigation to include camera screen
- ✅ Created ImageFileManager for platform-specific file handling
- ✅ Set up dependency injection with Koin

## Next Steps
- Test the camera functionality on Android devices
- Refine the book detection algorithm
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
- This simplified approach stores only one image per book, directly in the favorite book entity 