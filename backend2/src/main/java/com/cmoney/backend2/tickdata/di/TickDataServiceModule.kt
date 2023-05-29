package com.cmoney.backend2.tickdata.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.tickdata.service.TickDataService
import com.cmoney.backend2.tickdata.service.TickDataWeb
import com.cmoney.backend2.tickdata.service.TickDataWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val tickDataServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(TickDataService::class.java)
    }
    single<TickDataWeb> {
        TickDataWebImpl(
            manager = get(),
            tickDataService = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}