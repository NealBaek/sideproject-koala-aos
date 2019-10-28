# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-dontwarn javax.annotation.**

# okio
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**

# model
-keep class com.ksdigtalnomad.koala.data.models.** { *; }
-keepclassmembers class com.ksdigtalnomad.koala.data.models.** { *; }

# glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public class * extends com.bumptech.glide.module.AppGlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }

# retrolambda
-dontwarn java.lang.invoke.**

#-dontwarn android.databinding.**
#-keep class android.databinding.** { *; }



-ignorewarnings
-keep class * {
    public private *;
}

# Firebase Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# GMS
-dontwarn android.support.v4.**,org.slf4j.**,com.google.android.gms.**