# APulse Consumer ProGuard Rules

# Keep APulse public API
-keep class com.apulse.core.** { *; }
-keep class com.apulse.ui.** { public *; }

# Keep Room entities and DAOs
-keep class com.apulse.data.model.** { *; }
-keep class com.apulse.data.db.**Dao { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class **_HiltModules { *; }
-keep class **_Factory { *; }

# Keep Retrofit and OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Keep serialization classes
-keepclassmembers class com.apulse.export.model.** {
    <fields>;
}

# Keep Compose navigation
-keep class androidx.navigation.** { *; }