package com.cmoney.backend2.base.di

import android.content.Context
import com.cmoney.backend2.BuildConfig
import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactory
import com.cmoney.backend2.base.model.log.ApiLog
import com.cmoney.backend2.base.model.setting.BackendSettingSharedPreference
import com.cmoney.backend2.base.model.setting.DefaultSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.ConnectionSpec.Companion.COMPATIBLE_TLS
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
private const val DEFAULT_URL = "https://www.cmoney.tw/"
val BACKEND2_GSON = named("backend2_gson")
val BACKEND2_RETROFIT = named("backend2_retrofit")
val BACKEND2_SETTING = named("backend2_setting")

val backendBaseModule = module {
    single {
        BackendSettingSharedPreference(
            androidContext().getSharedPreferences(
                BackendSettingSharedPreference.TAG,
                Context.MODE_PRIVATE
            )
        )
    }
    single<Setting>(BACKEND2_SETTING) {
        DefaultSetting(backendSettingSharedPreference = get())
    }
    single<Gson>(BACKEND2_GSON) {
        GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
    }
    single<Retrofit>(BACKEND2_RETROFIT) {
        Retrofit.Builder()
            .baseUrl(DEFAULT_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON)))
            .addCallAdapterFactory(
                RecordApiLogCallAdapterFactory(
                    setting = get(BACKEND2_SETTING),
                    logDataRecorder = LogDataRecorder.getInstance()
                )
            )
            .build()
    }
}

/**
 * 創建預設的OkHttpClient
 */
private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectionSpecs(listOf(COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
        .addUrlInterceptor()
        .addLogInterceptor()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .callTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .build()
}

/**
 * 加入Http Body Log
 */
private fun OkHttpClient.Builder.addLogInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(interceptor)
    }
}

/**
 * 替換URL並加上Header的Log參數
 */
private fun OkHttpClient.Builder.addUrlInterceptor() = apply {
    val setting = getKoin().get<Setting>(BACKEND2_SETTING)
    val gson = getKoin().get<Gson>(BACKEND2_GSON)
    addInterceptor { chain ->
        val request: Request = chain.request()
        val domainUrl = setting.domainUrl
        val httpUrl = domainUrl.toHttpUrl()
        val newUrl = request.url.newBuilder()
            .scheme(httpUrl.scheme)
            .host(httpUrl.host)
            .build()
        val apiLogJson = ApiLog(
            appId = setting.appId,
            platform = setting.platform.code,
            appVersion = setting.appVersion,
            manufacturer = setting.manufacturer,
            model = setting.model,
            osVersion = setting.osVersion
        ).let { gson.toJson(it) }
        val newRequest = request.newBuilder()
            .addHeader("cmoneyapi-trace-context", apiLogJson)
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}

