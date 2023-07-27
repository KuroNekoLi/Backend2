package com.cmoney.backend2.base.di

import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactoryV2
import com.cmoney.backend2.base.model.log.ApiLog
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.setting.backend.BackendSetting
import com.cmoney.backend2.base.model.setting.backend.BackendSettingImpl
import com.cmoney.backend2.base.model.setting.backend.localdatasource.BackendSettingLocalDataSource
import com.cmoney.backend2.base.model.setting.backend.localdatasource.BackendSettingLocalDataSourceImpl
import com.cmoney.backend2.base.model.setting.jwt.JwtSetting
import com.cmoney.backend2.base.model.setting.jwt.JwtSettingImpl
import com.cmoney.backend2.base.model.setting.jwt.localdatasource.JwtSettingLocalDataSource
import com.cmoney.backend2.base.model.setting.jwt.localdatasource.JwtSettingLocalDataSourceImpl
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
val BACKEND2_RETROFIT_V2 = named("backend2_retrofit_v2")
val BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2 = named("backend2_retrofit_with_gson_non_serialize_nulls_v2")

val backendBaseModuleV2 = module {
    factory<BackendSettingLocalDataSource> {
        BackendSettingLocalDataSourceImpl(
            context = androidContext()
        )
    }
    factory<BackendSetting> {
        BackendSettingImpl(
            context = androidContext(),
            localDataSource = get()
        )
    }
    factory<JwtSettingLocalDataSource> {
        JwtSettingLocalDataSourceImpl(
            context = androidContext()
        )
    }
    factory<JwtSetting> {
        JwtSettingImpl(
            localDataSource = get()
        )
    }
    single {
        GlobalBackend2Manager.Builder.build(
            backendSetting = get(),
            jwtSetting = get()
        ) {

        }
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
    single(BACKEND2_RETROFIT_V2) {
        Retrofit.Builder()
            // 不會使用到預設的Url
            .baseUrl(DEFAULT_URL)
            .client(get(BACKEND2_OKHTTP_V2))
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON)))
            .addCallAdapterFactory(
                RecordApiLogCallAdapterFactoryV2(
                    manager = get(),
                    logDataRecorder = LogDataRecorder.getInstance()
                )
            )
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
                    manager = get(),
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
    val manager = getKoin().get<GlobalBackend2Manager>()
    if (manager.getIsDebug()) {
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
        val manager = getKoin().get<GlobalBackend2Manager>()
        val request: Request = chain.request()
        val httpUrl = manager.getGlobalDomainUrl().toHttpUrl()
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
    val manager = getKoin().get<GlobalBackend2Manager>()
    val gson = getKoin().get<Gson>(BACKEND2_GSON)
    addInterceptor { chain ->
        val request: Request = chain.request()
        val apiLogJson = ApiLog.create(
            appId = manager.getAppId(),
            platform = manager.getPlatform().code,
            appVersion = manager.getAppVersionName(),
            manufacturer = manager.getManufacturer(),
            model = manager.getModel(),
            osVersion = manager.getOsVersion()
        ).let { apiLog ->
            gson.toJson(apiLog)
        }
        val newRequest = request.newBuilder()
            .addHeader("cmoneyapi-trace-context", apiLogJson)
            .build()
        chain.proceed(newRequest)
    }
}