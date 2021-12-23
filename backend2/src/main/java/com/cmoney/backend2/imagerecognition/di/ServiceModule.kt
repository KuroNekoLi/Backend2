package com.cmoney.backend2.imagerecognition.di

import com.cmoney.backend2.base.di.*
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionService
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionWeb
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionWebImpl
import okhttp3.Call
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val DEBUG_URL = "https://outpost.cmoney.tw/"
private const val PRODUCTION_URL = "https://datateamapi.cmoney.tw/"
private val BACKEND2_IMAGE_RECOGNITION_RETROFIT = named("backend2_image_recognition_retrofit")
val BACKEND2_IMAGE_RECOGNITION_DEBUG = named("backend2_image_recognition_debug")
val BACKEND2_IMAGE_RECOGNITION_PRODUCTION = named("backend2_image_recognition_release")

val imageRecognitionServiceModule = module {
    factory(BACKEND2_IMAGE_RECOGNITION_RETROFIT) { (baseUrl: String) ->
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
    single<Retrofit>(BACKEND2_IMAGE_RECOGNITION_DEBUG) {
        get(BACKEND2_IMAGE_RECOGNITION_RETROFIT) {
            parametersOf(DEBUG_URL)
        }
    }
    single<Retrofit>(BACKEND2_IMAGE_RECOGNITION_PRODUCTION) {
        get(BACKEND2_IMAGE_RECOGNITION_RETROFIT) {
            parametersOf(PRODUCTION_URL)
        }
    }
    single(BACKEND2_IMAGE_RECOGNITION_DEBUG) {
        get<Retrofit>(BACKEND2_IMAGE_RECOGNITION_DEBUG).create(ImageRecognitionService::class.java)
    }
    single(BACKEND2_IMAGE_RECOGNITION_PRODUCTION) {
        get<Retrofit>(BACKEND2_IMAGE_RECOGNITION_PRODUCTION).create(ImageRecognitionService::class.java)
    }
    single<ImageRecognitionWeb>(BACKEND2_IMAGE_RECOGNITION_DEBUG) {
        ImageRecognitionWebImpl(
            setting = get(BACKEND2_SETTING),
            baseHost = DEBUG_URL,
            service = get(BACKEND2_IMAGE_RECOGNITION_DEBUG),
            gson = get(BACKEND2_GSON)
        )
    }
    single<ImageRecognitionWeb>(BACKEND2_IMAGE_RECOGNITION_PRODUCTION) {
        ImageRecognitionWebImpl(
            setting = get(BACKEND2_SETTING),
            baseHost = PRODUCTION_URL,
            service = get(BACKEND2_IMAGE_RECOGNITION_PRODUCTION),
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