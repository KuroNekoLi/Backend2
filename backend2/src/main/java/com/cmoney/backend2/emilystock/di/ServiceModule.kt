package com.cmoney.backend2.emilystock.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.emilystock.service.EmilyService
import com.cmoney.backend2.emilystock.service.EmilyWeb
import com.cmoney.backend2.emilystock.service.EmilyWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val emilyServiceModule = module {
    single<EmilyService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(EmilyService::class.java)
    }
    single<EmilyWeb> {
        EmilyWebImpl(get(BACKEND2_SETTING), get(), get(BACKEND2_GSON))
    }
}