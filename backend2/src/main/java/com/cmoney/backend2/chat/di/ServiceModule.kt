package com.cmoney.backend2.chat.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.chat.service.ChatRoomService
import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.ChatRoomWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val chatServiceModule = module {
    single<ChatRoomService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(ChatRoomService::class.java)
    }
    single<ChatRoomWeb> {
        ChatRoomWebImpl(get(), get(BACKEND2_SETTING), get(BACKEND2_GSON))
    }
}