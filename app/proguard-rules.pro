# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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

 -optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
 -optimizationpasses 5
 -allowaccessmodification
 #
 # Note that you cannot just include these flags in your own
 # configuration file; if you are including this file, optimization
 # will be turned off. You'll need to either edit this file, or
 # duplicate the contents of this file and remove the include of this
 # file from your project's proguard.config path property.

 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgent
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.app.Fragment

 # For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
 -keepclasseswithmembernames class * {
  native <methods>;
 }

 -keep public class * extends android.view.View {
  public <init>(android.content.Context);
  public <init>(android.content.Context, android.util.AttributeSet);
  public <init>(android.content.Context, android.util.AttributeSet, int);
  public void set*(...);
 }

 -keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet);
 }

 -keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet, int);
 }

 -keepclassmembers class * extends android.app.Activity {
  public void *(android.view.View);
 }

 # For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
 -keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
 }

 -keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
 }

 -keepclassmembers class **.R$* {
  public static <fields>;
 }

 -keep class android.support.v4.app.** { *; }
 -keep interface android.support.v4.app.** { *; }
 # The support library contains references to newer platform versions.
 # Don't warn about those in case this app is linking against an older
 # platform version. We know about them, and they are safe.
 -dontwarn android.support.**
 -dontwarn com.google.ads.**
 -dontwarn com.squareup.okhttp.**
 -dontwarn javax.annotation.**
 -dontwarn okio.**


 # Some methods are only called from tests, so make sure the shrinker keeps them.
 -keep class com.example.android.architecture.blueprints.** { *; }

 -keep class com.google.common.base.Preconditions { *; }
 -keep class android.databinding.** { *; }
 -keep class android.arch.** { *; }

 # For Guava:
 -dontwarn javax.annotation.**
 -dontwarn javax.inject.**
 -dontwarn sun.misc.Unsafe

 # Proguard rules that are applied to your test apk/code.
 -ignorewarnings

 -keepattributes *Annotation*

 -dontnote junit.framework.**
 -dontnote junit.runner.**

 -dontwarn android.test.**
 -dontwarn android.support.test.**
 -dontwarn org.junit.**
 -dontwarn org.hamcrest.**
 -dontwarn com.squareup.javawriter.JavaWriter
 # Uncomment this if you use Mockito
 -dontwarn org.mockito.**


 #/////////////

 # Some methods are only called from tests, so make sure the shrinker keeps them.
 #-keep class com.example.android.architecture.blueprints.** { *; }

 -keep class com.google.common.base.Preconditions { *; }
 -keep class android.databinding.** { *; }
 -keep class android.arch.** { *; }

 # For Guava:
 -dontwarn javax.annotation.**
 -dontwarn javax.inject.**
 -dontwarn sun.misc.Unsafe

 # Proguard rules that are applied to your test apk/code.
 -ignorewarnings

 -keepattributes *Annotation*

 -dontnote junit.framework.**
 -dontnote junit.runner.**

 -dontwarn android.test.**
 -dontwarn android.support.test.**
 -dontwarn org.junit.**
 -dontwarn org.hamcrest.**
 -dontwarn com.squareup.javawriter.JavaWriter
 # Uncomment this if you use Mockito
 -dontwarn org.mockito.**
 -dontwarn com.squareup.okhttp.**
 -dontwarn javax.annotation.**
 -dontwarn okio.**
 -dontwarn retrofit2.Platform$Java8

 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepclasseswithmembers class * {
     @retrofit2.http.* <methods>;
 }
 -keepclassmembernames interface * {
     @retrofit2.http.* <methods>;
 }

 -keepattributes Signature
 -keepattributes Exceptions

 -dontwarn com.squareup.**

 -keep class android.arch.** { *; }

 # Basic ProGuard rules for Firebase Android SDK 2.0.0+
 -keep class com.firebase.** { *; }
 -keep class org.apache.** { *; }
 -keepnames class com.fasterxml.jackson.** { *; }
 -keepnames class javax.servlet.** { *; }
 -keepnames class org.ietf.jgss.** { *; }
 -dontwarn org.w3c.dom.**

 # Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
 # EnclosingMethod is required to use InnerClasses.
 -keepattributes Signature, InnerClasses, EnclosingMethod

 # Retrofit does reflection on method and parameter annotations.
 -keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

 # Retain service method parameters when optimizing.
 -keepclassmembers,allowshrinking,allowobfuscation interface * {
     @retrofit2.http.* <methods>;
 }

 # Ignore annotation used for build tooling.
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

 # Ignore JSR 305 annotations for embedding nullability information.
 -dontwarn javax.annotation.**

 # Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
 -dontwarn kotlin.Unit

 # Top-level functions that can only be used by Kotlin.
 -dontwarn retrofit2.KotlinExtensions

 # With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
 # and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
 -if interface * { @retrofit2.http.* <methods>; }
 -keep,allowobfuscation interface <1>

-keep class androidx.annotation.Keep

-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}


-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

-keepnames class kotlinx.** { *; }

# Enums are not obfuscated correctly in combination with Gson
-keepclassmembers enum * { *; }

# The following are referenced but aren't required to run
-dontwarn com.fasterxml.jackson.**

# Android 6.0 release removes support for the Apache HTTP client
-dontwarn org.apache.http.**


-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

-keepattributes Signature,*Annotation*

-dontwarn javax.xml.stream.events.**
-dontwarn org.codehaus.jackson.**
-dontwarn org.apache.commons.logging.impl.**
-dontwarn org.apache.http.conn.scheme.**

-keep public class com.murat.dailysmoking.data.** { *; }
-keep public class com.murat.dailysmoking.db.entity.** { *; }
-keep public class com.murat.dailysmoking.db.embedded.** { *; }
-keep public class com.murat.dailysmoking.db.dao.** { *; }
-keep public class com.murat.dailysmoking.ui.graph.model.** { *; }
-keep public class com.murat.dailysmoking.utils.** { *; }

-keepattributes InnerClasses