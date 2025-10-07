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

**Note**: This is the initial release. APulse is inspired by [Pulse](https://github.com/kean/Pulse) for iOS but is a completely independent implementation built specifically for Android with modern development practices.