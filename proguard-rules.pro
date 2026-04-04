# ProGuard/R8 rules for inkcast-kmp

# --- R8 Optimization & Debugging ---
-dontusemixedcaseclassnames
-dontpreverify
-verbose
-keepattributes SourceFile, LineNumberTable

# --- Standard Android rules ---
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# --- Kotlin Coroutines ---
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}
-keep class kotlinx.coroutines.internal.MainDispatcherFactory { *; }
-keep class kotlinx.coroutines.CoroutineExceptionHandler { *; }
-keepclassmembernames class kotlinx.coroutines.internal.MainDispatcherFactory {
    public <init>();
}

# --- kotlinx-serialization ---
-keepattributes *Annotation*, EnclosingMethod, InnerClasses, Signature
-keepclassmembernames class kotlinx.serialization.json.** {
    *** serializer(...);
}
-keepclassmembernames class * {
    @kotlinx.serialization.Serializable <fields>;
    @kotlinx.serialization.Serializable *** Companion;
    @kotlinx.serialization.Serializable *** $serializer;
}

# --- Jetpack Compose ---
-keep class androidx.compose.ui.platform.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep @androidx.compose.runtime.Composable class * { *; }
-keepclassmembernames class * {
    @androidx.compose.runtime.Composable *;
}

# --- Ktor Client ---
-dontwarn java.lang.management.**
-dontwarn io.ktor.util.debug.IntellijIdeaDebugDetector
-keep class io.ktor.client.** { *; }
-keep class io.ktor.http.** { *; }
-keep class io.ktor.serialization.** { *; }
-keep class io.ktor.client.engine.okhttp.** { *; }

# --- OkHttp ---
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# --- Koin (Dependency Injection) ---
-keep class org.koin.** { *; }
-keepclassmembernames class * {
    @org.koin.core.annotation.* *;
}

# --- Orbit MVI ---
-keep class org.orbitmvi.orbit.** { *; }

# --- Multiplatform-Settings ---
-keep class com.russhwolf.settings.** { *; }

# --- FileKit ---
-keep class io.github.niclas_van_eyk.filekit.** { *; }
