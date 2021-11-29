package com.cmoney.backend2.chat.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.di.createOkHttpClient
import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactory
import com.cmoney.backend2.base.model.typeadapter.ULongTypeAdapter
import com.cmoney.backend2.chat.service.ChatRoomService
import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.ChatRoomWebImpl
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.gson.GsonBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DEFAULT_CHAT_URL = "https://talk.cmoney.tw/"
private val BACKEND2_CHAT_RETROFIT = named("backend2_chat_retrofit")
private val BACKEND2_CHAT_GSON = named("backend2_chat_gson")

val chatServiceModule = module {
    single(BACKEND2_CHAT_GSON) {
        GsonBuilder()
            .setLenient()
            .registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
            .create()
    }
    single(BACKEND2_CHAT_RETROFIT) {
        Retrofit.Builder()
            .baseUrl(DEFAULT_CHAT_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_CHAT_GSON)))
            .addCallAdapterFactory(
                RecordApiLogCallAdapterFactory(
                    setting = get(BACKEND2_SETTING),
                    logDataRecorder = LogDataRecorder.getInstance()
                )
            )
            .build()
    }
    single<ChatRoomService> {
        get<Retrofit>(BACKEND2_CHAT_RETROFIT).create(ChatRoomService::class.java)
    }
    single<ChatRoomWeb> {
        ChatRoomWebImpl(get(), get(BACKEND2_SETTING), get(BACKEND2_GSON))
    }
}