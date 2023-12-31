package com.cmoney.backend2.realtimeaftermarket.di

import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketService
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketWeb
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val realtimeAfterMarketServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(RealTimeAfterMarketService::class.java)
    }
    single<RealTimeAfterMarketWeb> {
        RealTimeAfterMarketWebImpl(
            manager = get(),
            service = get()
        )
    }
}