package com.cmoney.backend2.videochannel.di

import com.cmoney.backend2.base.di.*
import com.cmoney.backend2.videochannel.service.VideoChannelService
import com.cmoney.backend2.videochannel.service.VideoChannelWeb
import com.cmoney.backend2.videochannel.service.VideoChannelWebImpl
import okhttp3.Call
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val ICHECKAPP_BASE_URL = "https://www.icheckapp.com.tw/podcast/"
val BACKEND2_ICHECKAPP_RETROFIT = named("backend2_icheckapp_retrofit")

val videoChannelServiceModule = module {
    factory<Retrofit>(BACKEND2_ICHECKAPP_RETROFIT) {
        val backend2Retrofit = get<Retrofit>(BACKEND2_RETROFIT)
        val backend2RetrofitBuilder = backend2Retrofit.newBuilder()
        with(backend2RetrofitBuilder) {
            baseUrl(ICHECKAPP_BASE_URL)
            callFactory(createOkHttpClient(backend2Retrofit.callFactory()))
        }
        backend2RetrofitBuilder.build()
    }

    single<VideoChannelService> {
        get<Retrofit>(BACKEND2_ICHECKAPP_RETROFIT).create(VideoChannelService::class.java)
    }

    single<VideoChannelWeb> {
        VideoChannelWebImpl(
            get(),
            get(BACKEND2_GSON)
        )
    }
}

private fun createOkHttpClient(baseCallFactory: Call.Factory): OkHttpClient {
    val okHttpClientBuilder = if (baseCallFactory is OkHttpClient) {
        with(baseCallFactory.newBuilder()) {
            interceptors().clear()
            addLogInterceptor()
        }
    } else {
        OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
            .connectTimeout(30L, TimeUnit.SECONDS)
            .callTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
    }
    return okHttpClientBuilder.build()
}
