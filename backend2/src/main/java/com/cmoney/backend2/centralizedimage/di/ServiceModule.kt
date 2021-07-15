package com.cmoney.backend2.centralizedimage.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.centralizedimage.service.CentralizedImageService
import com.cmoney.backend2.centralizedimage.service.CentralizedImageWeb
import com.cmoney.backend2.centralizedimage.service.CentralizedImageWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val centralizedImageServiceModule = module {
    single<CentralizedImageService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(CentralizedImageService::class.java)
    }
    single<CentralizedImageWeb> {
        CentralizedImageWebImpl(get(), get(BACKEND2_SETTING), get(BACKEND2_GSON))
    }
}