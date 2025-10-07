# APulse Core Library ProGuard rules

# Keep public API classes
-keep public class com.apulse.core.** { 
    public *; 
}

# Keep OkHttp classes
-keepattributes Signature, InnerClasses, EnclosingMethod
-dontwarn okhttp3.**
-dontwarn okio.**

# Keep Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keep,includedescriptorclasses class com.apulse.core.**$$serializer { *; }
-keepclassmembers class com.apulse.core.** {
    *** Companion;
}
-keepclasseswithmembers class com.apulse.core.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }