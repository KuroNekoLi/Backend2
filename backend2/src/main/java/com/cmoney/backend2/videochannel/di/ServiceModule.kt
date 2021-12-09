package com.cmoney.backend2.videochannel.di

import com.cmoney.backend2.base.di.*
import com.cmoney.backend2.BuildConfig
import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactory
import com.cmoney.backend2.base.model.log.ApiLog
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.videochannel.service.VideoChannelService
import com.cmoney.backend2.videochannel.service.VideoChannelWeb
import com.cmoney.backend2.videochannel.service.VideoChannelWebImpl
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.gson.Gson
import okhttp3.ConnectionSpec
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val ICHECKAPP_BASE_URL = "https://www.icheckapp.com.tw/podcast/"
val ICHECKAPP_RETROFIT = named("icheckapp_retrofit")

val videoChannelServiceModule = module {
    single<Retrofit>(ICHECKAPP_RETROFIT) {
        Retrofit.Builder()
            .baseUrl(ICHECKAPP_BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON)))
            .build()
    }

    single<VideoChannelService> {
        get<Retrofit>(ICHECKAPP_RETROFIT).create(VideoChannelService::class.java)
    }

    single<VideoChannelWeb> {
        VideoChannelWebImpl(
            get(),
            get(BACKEND2_GSON)
        )
    }
}

/**
 * 創建預設的OkHttpClient
 */
private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
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
internal fun OkHttpClient.Builder.addLogInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(interceptor)
    }
}
