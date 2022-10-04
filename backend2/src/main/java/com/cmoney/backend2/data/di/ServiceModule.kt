package com.cmoney.backend2.data.di

import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.di.addLogInterceptor
import com.cmoney.backend2.data.service.DataService
import com.cmoney.backend2.data.service.DataWeb
import com.cmoney.backend2.data.service.DataWebImpl
import okhttp3.Call
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

typealias RetrofitProvider = () -> Retrofit

fun dataServiceModule(retrofitProvider: RetrofitProvider) = module {
    single<DataService> {
        retrofitProvider.invoke().create(DataService::class.java)
    }
    single<DataWeb> {
        DataWebImpl(
            service = get(),
            setting = get(BACKEND2_SETTING)
        )
    }
}

private val DATA_RETROFIT = named("cmoney_backend_data_retrofit")

val dataServiceModule = module {
    single(DATA_RETROFIT) {
        val backend2Retrofit = get<Retrofit>(BACKEND2_RETROFIT)
        val retrofitBuilder = backend2Retrofit.newBuilder()
        // 移除動態切換Host Interceptor
        retrofitBuilder
            .callFactory(createChatOkHttpClient(backend2Retrofit.callFactory()))
            .build()
    }
    single {
        get<Retrofit>(DATA_RETROFIT).create(DataService::class.java)
    }
    single<DataWeb> {
        DataWebImpl(service = get(), setting = get(BACKEND2_SETTING))
    }
}

private fun createChatOkHttpClient(baseCallFactory: Call.Factory): OkHttpClient {
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
