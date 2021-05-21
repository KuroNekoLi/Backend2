package com.cmoney.backend2.realtimeaftermarket.di

import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketService
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketWeb
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val realtimeAfterMarketServiceModule = module {
    single<RealTimeAfterMarketService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(RealTimeAfterMarketService::class.java)
    }
    single<RealTimeAfterMarketWeb> {
        RealTimeAfterMarketWebImpl(
            service = get(),
            setting = get(BACKEND2_SETTING)
        )
    }
}