package com.cmoney.backend2.virtualtrading2.di

import com.cmoney.backend2.base.di.*
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfigImpl
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2WebImpl
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val BACKEND2_VIRTUAL_TRADING2_OKHTTP = named("backend2_virtual_trading2_okhttp")
val BACKEND2_VIRTUAL_TRADING2_RETROFIT = named("backend2_virtual_trading2_retrofit")
val BACKEND2_VIRTUAL_TRADING2_REQUEST_CONFIG = named("backend2_virtual_trading2_request_config")

val virtualTrading2ServiceModule = module {
    single(BACKEND2_VIRTUAL_TRADING2_OKHTTP) {
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
    single(BACKEND2_VIRTUAL_TRADING2_RETROFIT) {
        Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(get(BACKEND2_VIRTUAL_TRADING2_OKHTTP))
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON_NON_SERIALIZE_NULLS)))
            //TODO 加入API LOG 攔截器
            .build()
    }
    single {
        get<Retrofit>(BACKEND2_VIRTUAL_TRADING2_RETROFIT).create(VirtualTrading2Service::class.java)
    }
    single<VirtualTradingRequestConfig>(BACKEND2_VIRTUAL_TRADING2_REQUEST_CONFIG) {
        VirtualTradingRequestConfigImpl(
            setting = get(BACKEND2_SETTING)
        )
    }
    single<VirtualTrading2Web> {
        VirtualTrading2WebImpl(
            requestConfig = get(BACKEND2_VIRTUAL_TRADING2_REQUEST_CONFIG),
            service = get(),
            gson = get(BACKEND2_GSON_NON_SERIALIZE_NULLS)
        )
    }
}