# APulse for Android

A comprehensive network monitoring and debugging tool for Android applications. APulse captures, stores, and analyzes HTTP(S) network traffic with advanced filtering, session management, and secure data handling capabilities.

## Features

### 🔍 Network Monitoring
- **Comprehensive Capture**: Captures all HTTP(S) requests and responses via OkHttp interceptor
- **Detailed Metrics**: Response times, payload sizes, status codes, and headers
- **WebView Support**: Optional Chromium NetLog bridge for WebView network capture
- **Real-time Monitoring**: Live network activity tracking with notifications

### 📊 Data Management
- **Session-based Organization**: Group network requests into named, savable sessions
- **Smart Storage**: Room database with LRU caching and automatic pruning
- **Search & Filter**: Powerful filtering by method, status, URL patterns, and more
- **Request Details**: Full request/response inspection with syntax highlighting

### 🔄 Export & Share
- **Multiple Formats**: Export to HAR, JSON, CSV, and native APulse formats
- **Secure Sharing**: Share sessions with colleagues via Android sharing system
- **Import Support**: Import and view shared APulse/HAR files in read-only mode
- **Batch Operations**: Export multiple sessions simultaneously

### 🔒 Security & Privacy
- **Advanced Redaction**: Intelligent data masking for sensitive information
- **Compliance Modes**: Built-in GDPR, HIPAA, and custom compliance templates
- **Encryption**: Android Keystore-based encryption for stored data
- **Granular Control**: Per-app, per-endpoint privacy controls

## Installation

### Quick Setup (3 Steps)

**1. Add Dependencies** to your app's `build.gradle.kts`:
```kotlin
// Add JitPack repository
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Core APulse library (network capture functionality, ~50KB)
    implementation("com.github.lukasz-pomianek.APulse:apulse-core:v1.0.18")
    
    // Full UI module for debug interface (~2MB) - debug builds only
    debugImplementation("com.github.lukasz-pomianek.APulse:app:v1.0.18")
    
    // Required OkHttp dependency
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}
```

**2. Initialize** in your `Application` class:
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        APulse.initialize(this)
    }
}
```

**3. Add Interceptor** to your OkHttp client:
```kotlin
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(APulse.createInterceptor(this))
    .build()
```

**Done!** APulse will capture all network traffic. Launch UI with `APulse.launch(context)`.

> 📖 **Detailed integration guide**: See [INTEGRATION.md](INTEGRATION.md) for real-world examples, build variants, and troubleshooting.

## Usage

### Launch APulse UI

```kotlin
// From debug menu or button
if (APulse.isAvailable()) {
    APulse.launch(context)
}

// Shake gesture (add to your activity)
if (BuildConfig.DEBUG) {
    shakeDetector = ShakeDetector { APulse.launch(this) }
}

// Direct to current session
APulse.launchCurrentSession(context)
```

### Configuration Options

```kotlin
APulse.initialize(this) {
    enableNotifications = true
    maxStorageSize = 100 * 1024 * 1024 // 100MB
    enableAutoRedaction = true
    retentionDays = 7
}
```

## Advanced Configuration

### Custom Redaction Rules

```kotlin
APulse.configure {
    redactionEngine {
        // Add custom patterns
        addRule(RedactionCategory.AUTHENTICATION) {
            pattern = "x-api-key: [A-Za-z0-9]+"
            replacement = "x-api-key: [REDACTED]"
            caseSensitive = false
        }
        
        // Custom category
        addCustomCategory("INTERNAL_IDS") {
            addPattern("user_id\":\\s*\\d+", "user_id\": \"[REDACTED]\"")
        }
    }
}
```

### Session Management

```kotlin
// Create a new session
val session = APulse.startSession("Login Flow Test")

// Switch to existing session
APulse.switchToSession(sessionId)

// Get current session info
val currentSession = APulse.getCurrentSession()

// Save session for later
APulse.saveSession(sessionId, "Saved Login Test")
```

### Export Configuration

```kotlin
val exportOptions = ExportOptions(
    format = ExportFormat.HAR,
    includeResponseBodies = true,
    applyRedaction = true,
    dateRange = DateRange.last7Days(),
    sessionIds = listOf("session1", "session2")
)

APulse.exportSessions(exportOptions) { result ->
    when (result) {
        is Success -> {
            // Handle successful export
            val exportedFile = result.data
        }
        is Error -> {
            // Handle export error
        }
    }
}
```

## Security Features

### Built-in Redaction Patterns

APulse automatically redacts common sensitive data:

- **Authentication**: Bearer tokens, API keys, OAuth tokens
- **Personal Data**: Email addresses, phone numbers, SSNs
- **Financial**: Credit card numbers, bank accounts
- **Passwords**: Password fields, secret keys
- **Cookies**: Session cookies, auth cookies

### Compliance Modes

```kotlin
APulse.setComplianceMode(ComplianceMode.GDPR) {
    // Automatically applies GDPR-compliant redaction rules
    enableDataMinimization = true
    requireExplicitConsent = true
    enableRightToErasure = true
}
```

### Custom Security Policies

```kotlin
val securityPolicy = SecurityPolicy {
    maxRequestBodySize = 1024 * 1024 // 1MB
    maxResponseBodySize = 1024 * 1024 // 1MB
    allowedContentTypes = listOf("application/json", "text/plain")
    
    // Block specific URLs
    blockedUrlPatterns = listOf(
        "*/admin/*",
        "*/internal/*"
    )
    
    // Require encryption for storage
    requireEncryption = true
}

APulse.applySecurityPolicy(securityPolicy)
```

## Integration Examples

### With Hilt/Dagger

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(APulse.createInterceptor(context))
            .build()
    }
}
```

### With Existing Logging

```kotlin
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(APulse.createInterceptor(context))
    .addInterceptor(HttpLoggingInterceptor()) // APulse works alongside other interceptors
    .build()
```

### Conditional Usage

```kotlin
val clientBuilder = OkHttpClient.Builder()

// Only add APulse in debug builds
if (BuildConfig.DEBUG) {
    clientBuilder.addInterceptor(APulse.createInterceptor(context))
}

val client = clientBuilder.build()
```

## API Reference

### APulse Core Methods

```kotlin
// Initialization
APulse.initialize(context: Context, config: APulseConfig.() -> Unit)

// Interceptor creation
APulse.createInterceptor(context: Context): Interceptor

// UI launches
APulse.launch(context: Context)
APulse.launchCurrentSession(context: Context)
APulse.launchSession(context: Context, sessionId: String)

// Session management
APulse.startSession(name: String): Session
APulse.switchToSession(sessionId: String)
APulse.getCurrentSession(): Session?
APulse.saveSession(sessionId: String, name: String)
APulse.deleteSession(sessionId: String)

// Export/Import
APulse.exportSessions(options: ExportOptions, callback: (Result<File>) -> Unit)
APulse.importFile(uri: Uri, callback: (Result<ImportResult>) -> Unit)

// Configuration
APulse.configure(config: APulseConfig.() -> Unit)
APulse.setComplianceMode(mode: ComplianceMode)
APulse.applySecurityPolicy(policy: SecurityPolicy)
```

### Data Models

```kotlin
data class Session(
    val id: String,
    val name: String,
    val createdAt: Long,
    val isActive: Boolean
)

data class NetworkRequest(
    val id: String,
    val sessionId: String,
    val method: String,
    val url: String,
    val statusCode: Int,
    val requestTimestamp: Long,
    val responseTimestamp: Long?,
    val duration: Long?,
    val requestSize: Int,
    val responseSize: Int
)

data class ExportOptions(
    val format: ExportFormat,
    val sessionIds: List<String>? = null,
    val includeRequestBodies: Boolean = true,
    val includeResponseBodies: Boolean = true,
    val applyRedaction: Boolean = true,
    val dateRange: DateRange? = null
)
```

## Sample App

The included sample app demonstrates APulse capabilities:

```bash
# Run the sample app
./gradlew :sample:installDebug

# The sample app includes test buttons for:
# - Basic CRUD operations (GET, POST, PUT, DELETE)
# - Authentication flows
# - Large data transfers
# - Error scenarios
# - Sensitive data handling
```

## Troubleshooting

### Common Issues

**Q: APulse not capturing requests**
A: Ensure the interceptor is added to your OkHttpClient and that you're using that client for network requests.

**Q: App crashes on export**
A: Check that you have proper storage permissions and sufficient disk space.

**Q: Redaction not working**
A: Verify that `applyRedaction` is enabled in your export options and that the patterns match your data format.

### Debug Mode

Enable verbose logging:
```kotlin
APulse.setDebugMode(true) // Adds detailed logs to help troubleshoot issues
```

### Performance Considerations

- APulse uses minimal memory through LRU caching
- Large responses are truncated by default (configurable)
- Background database operations don't block UI
- Automatic cleanup prevents storage bloat

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Legal

**Intellectual Property**: APulse is an independent, original implementation created for Android. While inspired by Pulse's concept, it contains no copied code and is built using different technologies and architecture. The network debugging concept itself is not proprietary and is common across many development tools.

**License**: This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

APulse is inspired by the excellent [Pulse](https://github.com/kean/Pulse) network debugging tool for Apple platforms by [Alexander Grebenyuk](https://github.com/kean). While APulse is a completely independent implementation built from scratch for Android, we acknowledge the inspiration drawn from Pulse's user experience and feature set.

**Key Differences:**
- **Platform**: APulse is designed specifically for Android with Kotlin/Compose vs iOS/macOS with Swift/SwiftUI
- **Architecture**: Uses Room database + OkHttp interceptors vs Core Data + URLSession
- **Implementation**: Completely original codebase with Android-specific optimizations
- **Features**: Android-focused features like Hilt integration, Material Design 3, and Android sharing

**Technology:**
- Built with modern Android development practices
- Uses Material Design 3 components  
- Powered by Jetpack Compose
- Leverages Android Keystore for security

---

**APulse** - Network debugging made simple for Android 🚀