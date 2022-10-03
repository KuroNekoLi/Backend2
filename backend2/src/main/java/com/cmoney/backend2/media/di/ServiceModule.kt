package com.cmoney.backend2.media.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.media.service.MediaService
import com.cmoney.backend2.media.service.MediaWeb
import com.cmoney.backend2.media.service.MediaWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val mediaServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(MediaService::class.java)
    }
    single<MediaWeb> {
        MediaWebImpl(get(BACKEND2_SETTING), get(), get(BACKEND2_GSON))
    }
}