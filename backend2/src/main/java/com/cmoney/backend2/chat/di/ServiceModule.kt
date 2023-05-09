package com.cmoney.backend2.chat.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2
import com.cmoney.backend2.chat.service.ChatRoomService
import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.ChatRoomWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val chatServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2).create(ChatRoomService::class.java)
    }
    single<ChatRoomWeb> {
        ChatRoomWebImpl(
            service = get(),
            manager = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}
