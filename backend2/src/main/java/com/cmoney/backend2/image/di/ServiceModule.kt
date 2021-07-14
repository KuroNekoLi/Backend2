package com.cmoney.backend2.image.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.image.service.ImageService
import com.cmoney.backend2.image.service.ImageWeb
import com.cmoney.backend2.image.service.ImageWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val imageServiceModule = module {
    single<ImageService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(ImageService::class.java)
    }
    single<ImageWeb> {
        ImageWebImpl(get(), get(BACKEND2_SETTING), get(BACKEND2_GSON))
    }
}