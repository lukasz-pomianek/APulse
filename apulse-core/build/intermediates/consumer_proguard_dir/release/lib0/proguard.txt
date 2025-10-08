# APulse Core Consumer ProGuard Rules
# These rules will be applied to projects that consume this library

# Keep APulse Core public API
-keep public class com.apulse.core.** { 
    public *; 
}

# Keep interceptor classes that might be used reflectively
-keep class com.apulse.core.interceptor.** { *; }

# Keep serialization classes
-keepclassmembers class com.apulse.core.** {
    *** Companion;
}
-keepclasseswithmembers class com.apulse.core.** {
    kotlinx.serialization.KSerializer serializer(...);
}