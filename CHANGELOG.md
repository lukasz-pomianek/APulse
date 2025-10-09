# Changelog

All notable changes to APulse will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-01-XX

### Added
- Initial release of APulse network debugging library for Android
- **Core Features**:
  - OkHttp interceptor for comprehensive HTTP(S) traffic capture
  - Room database with 7-entity schema for efficient data storage
  - Modern Jetpack Compose UI with Material Design 3
  - Session-based organization of network requests
  
- **Export & Import**:
  - Multiple export formats: HAR, JSON, CSV, APulse native
  - Import support for HAR and APulse files
  - Android sharing integration for team collaboration
  
- **Security & Privacy**:
  - Advanced redaction engine with regex-based pattern matching
  - Built-in compliance modes (GDPR, HIPAA)
  - Android Keystore encryption for stored data
  - Configurable data retention policies
  
- **UI Components**:
  - Network request list with search and filtering
  - Detailed request/response inspector
  - Session management interface
  - Security settings screen
  
- **Developer Experience**:
  - 3-step integration process
  - Lightweight core module (~50KB)
  - Debug-only UI module (~2MB)
  - Built-in no-op fallback for release builds
  - Comprehensive documentation and integration guide

- **Architecture**:
  - Modular design with `apulse-core` and main app modules
  - Hilt dependency injection
  - Coroutines for background processing
  - Factory pattern for clean separation of concerns
  
- **Sample Application**:
  - Complete demo app with various network scenarios
  - Test buttons for different HTTP methods
  - Authentication flow examples
  - Error handling demonstrations

### Technical Details
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Kotlin**: 1.9.22
- **Compose BOM**: 2024.02.00
- **Room**: 2.6.1
- **OkHttp**: 4.12.0
- **Hilt**: 2.48.1

### Dependencies
- androidx.core:core-ktx:1.12.0
- androidx.compose.ui:ui:2024.02.00
- androidx.room:room-runtime:2.6.1
- com.squareup.okhttp3:okhttp:4.12.0
- com.google.dagger:hilt-android:2.48.1
- org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2

---

## [1.0.26] - 2025-10-09

### Fixed
- JitPack build for `apulse-ktor`: add missing `consumer-rules.pro` and minimal `proguard-rules.pro`

### Docs
- README/INTEGRATION bumped to `v1.0.26`


## [1.0.24] - 2025-10-09

### Fixed
- Ktor plugin: replaced deprecated/incorrect hooks with `onRequest`/`onResponse` and fixed `cancel(null)` call to match API

### Docs
- README/INTEGRATION bumped to `v1.0.24`


## [1.0.22] - 2025-10-09

### Fixed
- UI ViewModels no longer touch Room on main thread:
  - `RequestListViewModel`: IO-thread for DAO calls, removed blocking `first()`; global search uses Flow+map
  - `SessionListViewModel`: IO-thread for create/update/delete/activate and getActiveSession flowOn(IO)
  - `SettingsViewModel`: IO-thread for destructive ops/export stubs

### Changed
- Docs bumped to `v1.0.22`


## [1.0.20] - 2025-10-09

### Fixed
- JitPack publishing reliability: unified project `group`/`version` and module `maven-publish` setup
- Correct JitPack coordinates in docs (`com.github.lukasz-pomianek:apulse-core` and `:apulse`)
- Compile errors in release build:
  - Qualified `RequestBody` in `APulseInterceptor` to avoid OkHttp collision
  - Replaced `Instant.minus(DateTimeUnit)` usage with Kotlin Duration (`olderThanDays.days`)
  - StateFlow typing fix in `SessionManager` (`flowOf<Session?>(null)`)

### Changed
- Simplified `jitpack.yml` to `publishToMavenLocal` for all modules
- Bumped docs to `v1.0.20`

### Notes
- Consumers: use JitPack tag `v1.0.20`

## Future Releases

### Planned Features
- WebView network capture support
- Real-time network monitoring with notifications  
- GraphQL request parsing
- Custom export templates
- Plugin system for extensibility
- Performance metrics and analytics
- Network request mocking/stubbing
- CI/CD integration tools

---

## [1.0.25] - 2025-10-09

### Added
- Auto-session creation on `APulse.initialize()` via optional initializer hook
- UI module implements `APulseInitializerImpl` to ensure default active session exists on app start

### Docs
- README/INTEGRATION updated; note that sessions are auto-created on initialize


## [1.0.23] - 2025-10-09

### Added
- New `apulse-ktor` module:
  - Ktor client plugin `APulsePlugin` for minimal capture on non-OkHttp engines
  - OkHttp engine helper `okhttpEngineWithAPulse(context)` to auto-attach APulse interceptor

### Docs
- README/INTEGRATION updated with Ktor setup and new artifact coordinates


## [1.0.21] - 2025-10-09

### Added
- Internal `com.apulse.ui.MainActivity` declared in library manifest (`android:exported="false"`) to host APulse Compose UI without client manifest changes

### Changed
- Docs updated to use JitPack tag `v1.0.21`


**Note**: This is the initial release. APulse is inspired by [Pulse](https://github.com/kean/Pulse) for iOS but is a completely independent implementation built specifically for Android with modern development practices.