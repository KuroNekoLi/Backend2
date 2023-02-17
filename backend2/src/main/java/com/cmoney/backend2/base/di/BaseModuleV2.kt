package com.cmoney.backend2.base.di

import com.cmoney.backend2.BuildConfig
import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactoryV2
import com.cmoney.backend2.base.model.log.ApiLog
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.gson.Gson
import okhttp3.ConnectionSpec
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * DI的模組定義
 */
private const val DEFAULT_URL = "http://localhost/"

/**
 * V2不會透過Setting來替換Domain，由每一個服務的RequestConfig進行轉接。
 */
val BACKEND2_OKHTTP_V2 = named("backend2_okhttp_v2")
val BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2 =
    named("backend2_retrofit_with_gson_non_serialize_nulls_v2")

val backendBaseModuleV2 = module {
    single {
        GlobalBackend2Manager.Builder(context = androidContext())
            .build()
    }
    single(BACKEND2_OKHTTP_V2) {
        OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
            .connectTimeout(30L, TimeUnit.SECONDS)
            .callTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addCMoneyApiTraceContextInterceptor()
            .addLogInterceptor()
            .build()
    }
    single(BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2) {
        Retrofit.Builder()
            // 不會使用到預設的Url
            .baseUrl(DEFAULT_URL)
            .client(get(BACKEND2_OKHTTP_V2))
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON_NON_SERIALIZE_NULLS)))
            .addCallAdapterFactory(
                RecordApiLogCallAdapterFactoryV2(
                    setting = get(BACKEND2_SETTING),
                    logDataRecorder = LogDataRecorder.getInstance()
                )
            )
            .build()
    }
}

/**
 * 加入Http Body Log
 */
internal fun OkHttpClient.Builder.addLogInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(interceptor)
    }
}

/**
 * 加入替換Domain攔截器，在Runtime時可以隨時替換Domain。
 */
internal fun OkHttpClient.Builder.addChangeDomainInterceptor() = apply {
    addInterceptor { chain ->
        val setting = getKoin().get<Setting>(BACKEND2_SETTING)
        val request: Request = chain.request()
        val httpUrl = setting.domainUrl.toHttpUrl()
        val newUrl = request.url.newBuilder()
            .scheme(httpUrl.scheme)
            .host(httpUrl.host)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}

/**
 * 加入CMoney的Header紀錄。
 */
internal fun OkHttpClient.Builder.addCMoneyApiTraceContextInterceptor() = apply {
    val setting = getKoin().get<Setting>(BACKEND2_SETTING)
    val gson = getKoin().get<Gson>(BACKEND2_GSON)
    addInterceptor { chain ->
        val request: Request = chain.request()
        val apiLogJson = ApiLog.create(
            appId = setting.appId,
            platform = setting.platform.code,
            appVersion = setting.appVersion,
            manufacturer = setting.manufacturer,
            model = setting.model,
            osVersion = setting.osVersion
        ).let { apiLog ->
            gson.toJson(apiLog)
        }
        val newRequest = request.newBuilder()
            .addHeader("cmoneyapi-trace-context", apiLogJson)
            .build()
        chain.proceed(newRequest)
    }
}