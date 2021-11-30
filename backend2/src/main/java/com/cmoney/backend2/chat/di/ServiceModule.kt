package com.cmoney.backend2.chat.di

import com.cmoney.backend2.base.di.*
import com.cmoney.backend2.chat.service.ChatRoomService
import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.ChatRoomWebImpl
import okhttp3.Call
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val RELEASE_CHAT_URL = "https://talk.cmoney.tw/"
private const val DEBUG_CHAT_URL = "http://192.168.10.103/"
private val BACKEND2_CHAT_RETROFIT = named("backend2_chat_retrofit")
val BACKEND2_CHAT_DEBUG = named("backend2_chat_debug")
val BACKEND2_CHAT_RELEASE = named("backend2_chat_release")

val chatServiceModule = module {
    factory(BACKEND2_CHAT_RETROFIT) { (baseUrl: String) ->
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
    single<Retrofit>(BACKEND2_CHAT_DEBUG) {
        get(BACKEND2_CHAT_RETROFIT) {
            parametersOf(DEBUG_CHAT_URL)
        }
    }
    single<Retrofit>(BACKEND2_CHAT_RELEASE) {
        get(BACKEND2_CHAT_RETROFIT) {
            parametersOf(RELEASE_CHAT_URL)
        }
    }
    single<ChatRoomService> {
        get<Retrofit>(BACKEND2_CHAT_RELEASE).create(ChatRoomService::class.java)
    }
    single<ChatRoomService>(BACKEND2_CHAT_RELEASE) {
        get<Retrofit>(BACKEND2_CHAT_RELEASE).create(ChatRoomService::class.java)
    }
    single<ChatRoomService>(BACKEND2_CHAT_DEBUG) {
        get<Retrofit>(BACKEND2_CHAT_DEBUG).create(ChatRoomService::class.java)
    }
    single<ChatRoomWeb> {
        ChatRoomWebImpl(get(), get(BACKEND2_SETTING), get(BACKEND2_GSON))
    }
    single<ChatRoomWeb>(BACKEND2_CHAT_DEBUG) {
        ChatRoomWebImpl(get(BACKEND2_CHAT_DEBUG), get(BACKEND2_SETTING), get(BACKEND2_GSON))
    }
    single<ChatRoomWeb>(BACKEND2_CHAT_RELEASE) {
        ChatRoomWebImpl(get(BACKEND2_CHAT_RELEASE), get(BACKEND2_SETTING), get(BACKEND2_GSON))
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