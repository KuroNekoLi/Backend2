package com.cmoney.backend2.centralizedimage.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.centralizedimage.service.CentralizedImageService
import com.cmoney.backend2.centralizedimage.service.CentralizedImageWeb
import com.cmoney.backend2.centralizedimage.service.CentralizedImageWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val centralizedImageServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(CentralizedImageService::class.java)
    }
    single<CentralizedImageWeb> {
        CentralizedImageWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}