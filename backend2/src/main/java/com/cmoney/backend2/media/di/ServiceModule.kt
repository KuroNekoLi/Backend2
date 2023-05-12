package com.cmoney.backend2.media.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.media.service.MediaService
import com.cmoney.backend2.media.service.MediaWeb
import com.cmoney.backend2.media.service.MediaWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val mediaServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(MediaService::class.java)
    }
    single<MediaWeb> {
        MediaWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}