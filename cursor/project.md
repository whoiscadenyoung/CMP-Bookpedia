# Bookpedia Project Structure

Bookpedia is a Kotlin Multiplatform book library application that allows users to search for books and mark them as favorites. The application is built using Compose Multiplatform and follows a clean architecture approach with data-domain-presentation layers.

## Project Architecture

The project follows a clean architecture approach with the following layers:
- **Data Layer**: Handles data operations, including API calls and database operations
- **Domain Layer**: Contains business logic and domain models
- **Presentation Layer**: Handles UI components and user interactions

The project also follows the MVI (Model-View-Intent) pattern for the presentation layer.

## Package Structure

### Core Components

- **core/domain**: Contains core domain models and utilities
  - `DataError.kt`: Defines error types for data operations
  - `Error.kt`: Base error interface
  - `Result.kt`: Result wrapper for handling success and error states

- **core/data**: Contains core data utilities
  - `HttpClientFactory.kt`: Factory for creating HTTP clients
  - `HttpClientExt.kt`: Extensions for HTTP client operations

- **core/presentation**: Contains core presentation utilities
  - `Colors.kt`: Defines color constants
  - `DataErrorToStringResource.kt`: Converts data errors to string resources
  - `PulseAnimation.kt`: Animation utility
  - `UiText.kt`: Text utility for UI

### Book Feature

- **book/domain**: Contains book domain models and interfaces
  - `Book.kt`: Domain model for a book
  - `BookRepository.kt`: Repository interface for book operations

- **book/data**: Contains book data implementations
  - **repository**: Repository implementation
    - `DefaultBookRepository.kt`: Implementation of the BookRepository interface
  - **database**: Database operations
    - `FavoriteBookDao.kt`: Data Access Object for favorite books
    - `FavoriteBookDatabase.kt`: Database definition
    - `BookEntity.kt`: Database entity for books
    - `DatabaseFactory.kt`: Factory for creating the database
  - **network**: Network operations
    - `RemoteBookDataSource.kt`: Interface for remote data source
    - `KtorRemoteBookDataSource.kt`: Ktor implementation of the remote data source
  - **dto**: Data Transfer Objects
    - Various DTOs for API responses
  - **mappers**: Mappers for converting between DTOs and domain models
    - `BookMapper.kt`: Maps between book DTOs and domain models

- **book/presentation**: Contains book presentation components
  - **book_list**: Book list screen
    - `BookListAction.kt`: Actions for the book list
    - `BookListScreen.kt`: UI for the book list
    - `BookListState.kt`: State for the book list
    - `BookListViewModel.kt`: ViewModel for the book list
  - **book_detail**: Book detail screen
    - `BookDetailAction.kt`: Actions for the book detail
    - `BookDetailScreen.kt`: UI for the book detail
    - `BookDetailState.kt`: State for the book detail
    - `BookDetailViewModel.kt`: ViewModel for the book detail
  - `SelectedBookViewModel.kt`: Shared ViewModel for selected book

- **book/app**: Contains application components
  - `App.kt`: Main application component
  - `Route.kt`: Navigation routes

### Dependency Injection

- **di**: Contains dependency injection modules
  - `Modules.kt`: Defines Koin modules
  - `initKoin.kt`: Initializes Koin

## Platform-Specific Implementations

- **androidMain**: Android-specific implementations
  - `BookApplication.kt`: Android application class
  - `MainActivity.kt`: Main activity
  - `Previews.kt`: Compose previews

- **iosMain**: iOS-specific implementations
  - `MainViewController.kt`: Main view controller

- **desktopMain**: Desktop-specific implementations
  - `main.kt`: Main entry point for desktop

## Key Technologies

- **Compose Multiplatform**: UI framework
- **Koin**: Dependency injection
- **Room**: Database operations
- **Ktor**: Network operations
- **Kotlinx Serialization**: JSON serialization
- **Compose Navigation**: Navigation between screens

## Application Flow

1. The application starts with the `App.kt` component, which sets up the navigation.
2. The navigation starts with the `BookList` route, which displays a list of books.
3. When a book is selected, the application navigates to the `BookDetails` route, which displays the details of the selected book.
4. Users can mark books as favorites, which are stored in the local database.
5. Users can view their favorite books in the book list.

## Data Flow

1. The `BookListViewModel` requests books from the `BookRepository`.
2. The `BookRepository` fetches books from the remote data source or the local database.
3. The data is mapped to domain models and returned to the ViewModel.
4. The ViewModel updates the state, which is observed by the UI.
5. The UI displays the data to the user.

## Dependency Management

- Koin is used for dependency injection throughout the application.
- Room is used for database operations.
- Ktor is used for network operations. 