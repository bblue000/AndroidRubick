# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\MyWork\Android\adt-4.4W\sdk/tools/proguard/proguard-android.txt
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

# 对大小写不敏感的系统而言，有效Aa aA
-dontusemixedcaseclassnames
-dontpreverify
-keepattributes *Annotation*


# dontwarn start
# 一类使用到扩展类的地方
-dontwarn java.beans.**
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn javax.annotation.**
-dontwarn javax.lang.**
-dontwarn javax.tools.**
-dontwarn sun.misc.**

# butterknife
-dontwarn butterknife.internal.**

# dontwarn end



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

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}



# 框架中的保留类
-keep class androidrubick.xbase.util.spi.XJsonParserService
-keep class androidrubick.xframework.job.spi.XJobExecutorService
-keep class androidrubick.xframework.cache.spi.XMemCacheService
-keep class androidrubick.xframework.net.http.spi.XHttpRequestService
-keep class androidrubick.xframework.api.spi.XAPIService

-keep class * implements androidrubick.xbase.util.spi.XJsonParserService
-keep class * implements androidrubick.xframework.job.spi.XJobExecutorService
-keep class * implements androidrubick.xframework.cache.spi.XMemCacheService
-keep class * implements androidrubick.xframework.net.http.spi.XHttpRequestService
-keep class * implements androidrubick.xframework.api.spi.XAPIService

