package com.cmoney.backend2.crawlsetting.di

import com.cmoney.backend2.base.di.*
import com.cmoney.backend2.crawlsetting.service.CrawlSettingService
import com.cmoney.backend2.crawlsetting.service.CrawlSettingWeb
import com.cmoney.backend2.crawlsetting.service.CrawlSettingWebImpl
import okhttp3.Call
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val DEBUG_URL = "https://apitest-inner.cmoney.tw/"
private const val PRODUCTION_URL = "https://datateamapi.cmoney.tw/"
private val BACKEND2_CRAWL_SETTING_RETROFIT = named("backend2_crawl_setting_retrofit")
val BACKEND2_CRAWL_SETTING_DEBUG = named("backend2_crawl_setting_debug")
val BACKEND2_CRAWL_SETTING_PRODUCTION = named("backend2_crawl_setting_production")

val crawlSettingServiceModule = module {
    factory(BACKEND2_CRAWL_SETTING_RETROFIT) { (baseUrl: String) ->
        val backend2Retrofit = get<Retrofit>(BACKEND2_RETROFIT)
        val backend2RetrofitBuilder = backend2Retrofit.newBuilder()
        with(backend2RetrofitBuilder) {
            baseUrl(baseUrl)
            callFactory(createChatOkHttpClient(backend2Retrofit.callFactory()))
            converterFactories().clear()
            addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON_NON_SERIALIZE_NULLS)))
        }
        backend2RetrofitBuilder.build()
    }
    single<Retrofit>(BACKEND2_CRAWL_SETTING_DEBUG) {
        get(BACKEND2_CRAWL_SETTING_RETROFIT) {
            parametersOf(DEBUG_URL)
        }
    }
    single<Retrofit>(BACKEND2_CRAWL_SETTING_PRODUCTION) {
        get(BACKEND2_CRAWL_SETTING_RETROFIT) {
            parametersOf(PRODUCTION_URL)
        }
    }
    single(BACKEND2_CRAWL_SETTING_DEBUG) {
        get<Retrofit>(BACKEND2_CRAWL_SETTING_DEBUG).create(CrawlSettingService::class.java)
    }
    single(BACKEND2_CRAWL_SETTING_PRODUCTION) {
        get<Retrofit>(BACKEND2_CRAWL_SETTING_PRODUCTION).create(CrawlSettingService::class.java)
    }
    single<CrawlSettingWeb>(BACKEND2_CRAWL_SETTING_DEBUG) {
        CrawlSettingWebImpl(
            setting = get(BACKEND2_SETTING),
            baseHost = DEBUG_URL,
            service = get(BACKEND2_CRAWL_SETTING_DEBUG),
            gson = get(BACKEND2_GSON)
        )
    }
    single<CrawlSettingWeb>(BACKEND2_CRAWL_SETTING_PRODUCTION) {
        CrawlSettingWebImpl(
            setting = get(BACKEND2_SETTING),
            baseHost = PRODUCTION_URL,
            service = get(BACKEND2_CRAWL_SETTING_PRODUCTION),
            gson = get(BACKEND2_GSON)
        )
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
