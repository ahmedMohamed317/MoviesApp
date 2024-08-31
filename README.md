# MoviesApp

MoviesApp is a Kotlin-based Android application that retrieves and displays a list of movies released in 2024. Users can view movie details, including the poster, description, rating, language, and release year. The app also allows users to mark movies as favorites.

## Features

- **Browse Movies:** Retrieve and display a list of movies released in 2024.
- **Movie Details:** View detailed information about each movie, including poster, description, rating, language, and release year.
- **Favorite Movies:** Mark movies as favorites and manage your list of favorite movies.
- **Pagination:** Smooth scrolling through movies with efficient data loading.
- **Offline Support:** Favorite movies are stored locally using Room.

## Technologies Used

- **MVVM Architecture:** Ensures a clear separation of concerns and facilitates testing.
- **Room:** Local database management for storing favorite movies.
- **Koin:** Dependency injection for managing and providing dependencies in the app.
- **Retrofit:** Networking library used for making API requests to fetch movies.
- **LiveData:** Observes and reacts to data changes, ensuring UI components are updated accordingly.
- **Unit Testing:** Test the app's components to ensure robustness and reliability.
- **Pagination:** Efficiently load and display movie data as the user scrolls through the list.
- **Glide:** Efficiently load and display image posters.
- **XML:** Defines UI layout and design.
- **Kotlin Coroutines:** Manages asynchronous operations and background tasks efficiently.

### Installation

- Clone the repo:
https://github.com/ahmedMohamed317/MoviesApp.git
