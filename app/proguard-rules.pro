# Keep stack traces useful in crash reports.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**

# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keep,includedescriptorclasses class com.service.**$$serializer { *; }
-keepclassmembers class com.service.** {
    *** Companion;
}
-keepclasseswithmembers class com.service.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Hilt / Dagger
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper

# Room

-dontwarn androidx.room.paging.**

# Data classes used by serialization / Room reflection
-keep class com.service.entity.** { *; }

# Compose
-keep class androidx.compose.runtime.** { *; }
