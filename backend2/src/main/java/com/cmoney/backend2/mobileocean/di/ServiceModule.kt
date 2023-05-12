package com.cmoney.backend2.mobileocean.di

import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.mobileocean.service.MobileOceanService
import com.cmoney.backend2.mobileocean.service.MobileOceanWeb
import com.cmoney.backend2.mobileocean.service.MobileOceanWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val mobileOceanServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(MobileOceanService::class.java)
    }
    single<MobileOceanWeb> {
        MobileOceanWebImpl(
            manager = get(),
            service = get()
        )
    }
}