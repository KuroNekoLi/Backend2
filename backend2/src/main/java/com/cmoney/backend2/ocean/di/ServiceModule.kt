package com.cmoney.backend2.ocean.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.ocean.service.OceanService
import com.cmoney.backend2.ocean.service.OceanWeb
import com.cmoney.backend2.ocean.service.OceanWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val oceanServiceModule = module {
    single<OceanService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(OceanService::class.java)
    }
    single<OceanWeb> {
        OceanWebImpl(get(BACKEND2_GSON), get(), get(BACKEND2_SETTING))
    }
}