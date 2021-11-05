##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.cmoney.backend2.base.model.request.** { *; }
-keep class com.cmoney.backend2.base.model.response.** { *; }
-keep class com.cmoney.backend2.base.model.setting.** { *; }
-keep class com.cmoney.backend2.base.model.log.** { *; }
-keep class com.cmoney.backend2.activity.service.api.** { *; }
-keep class com.cmoney.backend2.additioninformationrevisit.service.api.** { *; }
-keep class com.cmoney.backend2.authorization.service.api.** { *; }
-keep class com.cmoney.backend2.billing.service.api.** { *; }
-keep class com.cmoney.backend2.billing.service.common.** { *; }
-keep class com.cmoney.backend2.cellphone.service.api.** { *; }
-keep class com.cmoney.backend2.centralizedimage.service.api.** { *; }
-keep class com.cmoney.backend2.chat.service.api.** { *; }
-keep class com.cmoney.backend2.chipk.service.api.** { *; }
-keep class com.cmoney.backend2.cmtalk.service.api.** { *; }
-keep class com.cmoney.backend2.common.service.api.** { *; }
-keep class com.cmoney.backend2.crm.service.api.** { *; }
-keep class com.cmoney.backend2.customgroup.service.api.** { *; }
-keep class com.cmoney.backend2.customgroup2.service.api.** { *; }
-keep class com.cmoney.backend2.dtno.service.api.** { *; }
-keep class com.cmoney.backend2.emilystock.service.api.** { *; }
-keep class com.cmoney.backend2.forumocean.service.api.** { *; }
-keep class com.cmoney.backend2.identityprovider.service.api.** { *; }
-keep class com.cmoney.backend2.media.service.api.** { *; }
-keep class com.cmoney.backend2.mobileocean.service.api.** { *; }
-keep class com.cmoney.backend2.note_extension.service.api.** { *; }
-keep class com.cmoney.backend2.notes.service.api.** { *; }
-keep class com.cmoney.backend2.notification.service.api.** { *; }
-keep class com.cmoney.backend2.notification2.service.api.** { *; }
-keep class com.cmoney.backend2.ocean.service.api.** { *; }
-keep class com.cmoney.backend2.portal.service.api.** { *; }
-keep class com.cmoney.backend2.profile.service.api.** { *; }
-keep class com.cmoney.backend2.realtimeaftermarket.service.api.** { *; }
-keep class com.cmoney.backend2.tickdata.service.api.** { *; }
-keep class com.cmoney.backend2.trial.service.api.** { *; }
-keep class com.cmoney.backend2.virtualassets.service.api.** { *; }
-keep class com.cmoney.backend2.userbehavior.service.api.** { *; }
-keep class com.cmoney.backend2.clientconfiguration.service.api.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##---------------End: proguard configuration for Gson  ----------

## Okhttp Start
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
## Okhttp End

## Retrofit Start

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
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

##Retrofit End
