# APulse Integration Guide

This guide explains how to integrate APulse into any Android project as an external library.

## Library Structure

APulse is distributed as two modules:

- **`apulse-core`** (Required): Lightweight API with OkHttp interceptor
- **`apulse`** (Optional): Full UI and debugging features for debug builds

## Quick Integration (3 Steps)

### 1. Add Dependencies

**In your app's `build.gradle.kts`:**

```kotlin
dependencies {
    // Core network capture functionality (~50KB)
    implementation("com.github.lukasz-pomianek:apulse-core:v1.0.28")
    
    // Full UI module for debug interface (~2MB) - debug builds only
    debugImplementation("com.github.lukasz-pomianek:apulse:v1.0.28")
    // Optional: Ktor plugin
    debugImplementation("com.github.lukasz-pomianek:apulse-ktor:v1.0.28")
    
    // Your existing OkHttp dependency
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    
    // Add JitPack repository
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Initialize APulse

**In your `Application` class:**

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        APulse.initialize(this) {
            enableNotifications = true
            maxStorageSize = 50 * 1024 * 1024 // 50MB
            enableAutoRedaction = true
        }
    }
}
```

### 3. Add Interceptor to OkHttp

```kotlin
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(APulse.createInterceptor(this))
    .build()
```

**That's it!** APulse will now capture all your network traffic.
Default active session is auto-created during `APulse.initialize()`.

## Usage in Your App

### Launch APulse UI

```kotlin
// Option 1: From a debug menu
if (APulse.isAvailable()) {
    APulse.launch(this)
}

// Option 2: Shake gesture (in debug builds)
class MainActivity : AppCompatActivity() {
    
    private lateinit var shakeDetector: ShakeDetector
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (BuildConfig.DEBUG && APulse.isAvailable()) {
            shakeDetector = ShakeDetector {
                APulse.launch(this)
            }
            shakeDetector.start(this)
        }
    }
}

// Option 3: Floating action button
FloatingActionButton(
    onClick = { 
        if (APulse.isAvailable()) {
            APulse.launch(context)
        }
    }
) {
    Icon(Icons.Default.BugReport, contentDescription = "Network Debug")
}
```

### Access Current Session

```kotlin
// Launch directly to current session
APulse.launchCurrentSession(context)

// Or get session info programmatically
val session = APulse.getCurrentSession()
println("Current session: ${session?.name}, Requests: ${session?.requestCount}")
```

## Real-World Integration Examples

### With Retrofit

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(APulse.createInterceptor(context))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                   else HttpLoggingInterceptor.Level.NONE
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.yourapp.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()
}
```

### With Ktor Client

```kotlin
val client = HttpClient(OkHttp) {
    engine {
        config {
            addInterceptor(APulse.createInterceptor(context))
        }
    }
}
```

### With Volley

```kotlin
class APulseVolleyStack : HurlStack() {
    override fun createConnection(url: URL?): HttpURLConnection {
        val connection = super.createConnection(url)
        
        // Note: Volley uses HttpURLConnection, not OkHttp
        // APulse works best with OkHttp-based networking
        // Consider migrating to Retrofit for better APulse integration
        
        return connection
    }
}
```

## Multiple Build Variants

### Different Configurations Per Build Type

```kotlin
android {
    buildTypes {
        debug {
            buildConfigField("boolean", "ENABLE_APULSE", "true")
            buildConfigField("long", "APULSE_MAX_SIZE", "100L * 1024 * 1024") // 100MB
        }
        release {
            buildConfigField("boolean", "ENABLE_APULSE", "false")
            buildConfigField("long", "APULSE_MAX_SIZE", "10L * 1024 * 1024") // 10MB
        }
        staging {
            buildConfigField("boolean", "ENABLE_APULSE", "true")
            buildConfigField("long", "APULSE_MAX_SIZE", "50L * 1024 * 1024") // 50MB
        }
    }
}
```

```kotlin
// In your Application class
APulse.initialize(this) {
    enableNotifications = BuildConfig.ENABLE_APULSE
    maxStorageSize = BuildConfig.APULSE_MAX_SIZE
    enableAutoRedaction = !BuildConfig.DEBUG // More redaction in non-debug builds
}
```

## Advanced Configuration

### Custom Security Rules

```kotlin
APulse.initialize(this) {
    enableAutoRedaction = true
    
    // Custom patterns
    addRedactionRule("Internal User ID", "user_id\":\\s*\\d+", "user_id\": \"[REDACTED]\"")
    addRedactionRule("Session Token", "session_token=[^&\\s]+", "session_token=[REDACTED]")
    
    // Size limits
    maxRequestBodySize = 512 * 1024  // 512KB
    maxResponseBodySize = 1024 * 1024 // 1MB
    
    // Storage
    retentionDays = 3 // Keep data for 3 days only
    enableEncryption = true
}
```

### Conditional Integration

```kotlin
// Only enable APulse for internal builds
val enableAPulse = BuildConfig.DEBUG || 
                   BuildConfig.FLAVOR == "internal" ||
                   isInternalTester()

if (enableAPulse) {
    val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(APulse.createInterceptor(this))
    
    // Add other debug interceptors
    if (BuildConfig.DEBUG) {
        clientBuilder.addInterceptor(ChuckerInterceptor(this))
    }
    
    client = clientBuilder.build()
} else {
    client = OkHttpClient.Builder().build()
}
```

## Production Considerations

### Size Impact

- **`apulse-core`**: ~50KB (minimal impact)
- **`apulse`** (full UI): ~2MB (debug builds only)

### Performance Impact

- **CPU**: ~1-2% overhead for network calls
- **Memory**: ~10-50MB depending on traffic volume
- **Storage**: Configurable, default 100MB max

### Security in Release Builds

```kotlin
APulse.initialize(this) {
    // Production-safe defaults
    enableAutoRedaction = true
    enableEncryption = true
    maxStorageSize = 10 * 1024 * 1024 // 10MB max in production
    retentionDays = 1 // Delete data quickly
    
    // Block sensitive endpoints
    blockedUrlPatterns = listOf(
        "*/admin/*",
        "*/internal/*",
        "*/payment/*"
    )
}
```

## Testing Integration

### Verify APulse is Working

```kotlin
class APulseIntegrationTest {
    
    @Test
    fun testAPulseCapture() = runTest {
        // Make a test request
        val response = apiService.getUsers()
        
        // Verify it was captured
        delay(100) // Allow time for capture
        
        val session = APulse.getCurrentSession()
        assertThat(session?.requestCount).isGreaterThan(0)
    }
}
```

### Sample Network Calls

```kotlin
// Add this to your debug builds for testing
class NetworkTestHelper {
    
    fun generateTestTraffic() {
        // Basic GET request
        apiService.getUsers()
        
        // POST with body
        apiService.createUser(CreateUserRequest("Test User"))
        
        // Large response
        apiService.getLargeDataset()
        
        // Authentication request (will be redacted)
        apiService.login(LoginRequest("user", "password"))
        
        // Error request
        apiService.triggerError()
    }
}
```

## Troubleshooting

### APulse Not Capturing Requests

1. **Check interceptor order**: APulse interceptor should be added first
2. **Verify initialization**: Call `APulse.initialize()` before creating OkHttp client
3. **Debug logs**: Enable `APulse.setDebugMode(true)` to see internal logs

```kotlin
// Correct order
val client = OkHttpClient.Builder()
    .addInterceptor(APulse.createInterceptor(context)) // First
    .addInterceptor(HttpLoggingInterceptor()) // After APulse
    .build()
```

### UI Not Available

```kotlin
// Always check availability before launching
if (APulse.isAvailable()) {
    APulse.launch(context)
} else {
    Toast.makeText(context, "APulse not available in this build", Toast.LENGTH_SHORT).show()
}
```

### Memory Issues

```kotlin
// Reduce memory usage
APulse.initialize(this) {
    maxStorageSize = 20 * 1024 * 1024 // 20MB
    maxRequestBodySize = 100 * 1024   // 100KB
    maxResponseBodySize = 200 * 1024  // 200KB
    retentionDays = 1                 // Keep data for 1 day only
}
```

## Migration from Other Tools

### From Chuck/Chucker

```kotlin
// Before
debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

.addInterceptor(ChuckerInterceptor.Builder(context).build())

// After
implementation("com.github.lukasz-pomianek:apulse-core:v1.0.28")
debugImplementation("com.github.lukasz-pomianek:apulse:v1.0.28")
debugImplementation("com.github.lukasz-pomianek:apulse-ktor:v1.0.28")

.addInterceptor(APulse.createInterceptor(context))
```

### From Flipper Network Plugin

```kotlin
// Before
debugImplementation('com.facebook.flipper:flipper-network-plugin:0.150.0')

client.addInterceptor(new FlipperOkhttpInterceptor(networkFlipperPlugin))

// After
implementation("com.github.lukasz-pomianek:apulse-core:v1.0.28")
debugImplementation("com.github.lukasz-pomianek:apulse:v1.0.28")
debugImplementation("com.github.lukasz-pomianek:apulse-ktor:v1.0.28")

.addInterceptor(APulse.createInterceptor(context))
```

## Next Steps

1. **Integrate APulse** using the 3-step setup above
2. **Test the integration** by making network requests and launching APulse UI
3. **Customize configuration** based on your app's needs
4. **Set up debug menu** or shake gesture for easy access
5. **Configure production settings** for release builds

For more advanced features, see the main [README.md](README.md) for session management, export/import, and security configurations.