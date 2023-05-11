package com.cmoney.backend2.emilystock.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.emilystock.service.EmilyService
import com.cmoney.backend2.emilystock.service.EmilyWeb
import com.cmoney.backend2.emilystock.service.EmilyWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val emilyServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(EmilyService::class.java)
    }
    single<EmilyWeb> {
        EmilyWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}