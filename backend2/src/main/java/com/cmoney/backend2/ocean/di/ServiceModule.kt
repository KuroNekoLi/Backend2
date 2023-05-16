package com.cmoney.backend2.ocean.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.ocean.service.OceanService
import com.cmoney.backend2.ocean.service.OceanWeb
import com.cmoney.backend2.ocean.service.OceanWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val oceanServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(OceanService::class.java)
    }
    single<OceanWeb> {
        OceanWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}