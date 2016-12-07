# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\iiMedia\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
# sdk
-dontwarn com.iimedia.xwsdk.**
-keep public class com.iimedia.xwsdk.**{*;}
# facebook
-dontwarn com.facebook.**
-keep class com.facebook.**{*;}
# google
-dontwarn com.google.**
-keep class com.google.**{*;}
# volley
-dontwarn com.android.volley.**
-keep class com.android.volley.**{*;}
# image loader
-dontwarn com.nostra13.**
-keep public class com.nostra13.**{*;}
# 保持哪些类不被混淆
-keep public class * extends android.app.Application
# 保持哪些类不被混淆
-keep public class * extends android.app.Service
# 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver
# 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider
# 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper
# 保持哪些类不被混淆
-keep public class * extends android.preference.Preference
# 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * implements java.io.Serializable {*;}
# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}