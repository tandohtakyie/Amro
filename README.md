# Amro

AMRO is a sophisticated Android application designed for exploring global trending movies. It serves as a flagship demonstration of modern Android development, featuring a robust offline-first architecture, premium UI/UX, and high-precision engineering standards.

## Product Experience

- **Curated Trending Feed**: Real-time access to the top 100 trending movies worldwide.
- **Precision Filtering**: Instant genre-based filtering to narrow down your discovery.
- **Multidimensional Sorting**: Full control over your feed with sorting by Popularity, Title, Release Date, and Rating (supporting both Ascending and Descending orders).
- **Deep-Dive Details**: Comprehensive movie profiles featuring financial statistics (Budget/Revenue), immersive overviews, and direct IMDb integration.
- **Reliable Offline Access**: Continued access to the movie registry and recently viewed details, even without an active internet connection.
- **Sleek Interface**: A premium Dark Mode experience built with customized Material 3 components.

## Technical Foundation

The application is built on a **Modular Clean Architecture** to ensure industrial-grade scalability and testability.

### Architecture Highlights

- **Modularization**: Decoupled by both Layer (`core:data`, `core:network`) and Feature (`feature:trending`, `feature:detail`) to minimize build times and maximize code reuse.
- **Offline-First Strategy**: Utilizes **Store5** to orchestrate seamless synchronization between the remote TMDB API and the Room-based local Source of Truth.
- **Reactive State (MVI)**: Implements precise state management via a unidirectional data flow, ensuring the UI is a pure reflection of the underlying reactive state.
- **Convention Plugins**: Centralized build logic managed within `/build-logic` for project-wide consistency.

### Tech Stack

- **Core**: Kotlin, Coroutines, Flow
- **UI**: Jetpack Compose (Material 3), Coil 3 (Image Loading)
- **Data**: Store5, Room (Persistence), Retrofit/OkHttp (Networking)
- **Navigation**: Jetpack Navigation 3 (Nav3)
- **DI**: Hilt

## Engineering Excellence

- **Automated Testing**: Robust validation of ViewModels, Domain logic, and state transitions using MockK and Turbine.
- **Technical Documentation**: High-priority business logic and reactive streams are fully documented via KDocs for future-proof maintainability.
- **UI Standards**: 100% adherence to Jetpack Compose best practices.

## Environment Setup

### 1. Prerequisites
- Android Studio Ladybug or newer.
- A [TMDB API Key](https://developer.themoviedb.org/docs/getting-started).

### 2. Configuration
Add your API key to the `local.properties` file in the project root:
```properties
API_KEY_TMDB=your_api_key_here
```

### 3. Execution
Sync the project and run via the IDE, or use the command line:
```bash
./gradlew :app:assembleDebug
```
