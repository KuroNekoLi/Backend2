package com.cmoney.backend2.vtwebapi.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.vtwebapi.service.VirtualTradeService
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWeb
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val virtualTradeServiceModule = module {
    single<VirtualTradeService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(VirtualTradeService::class.java)
    }
    single<VirtualTradeWeb> {
        VirtualTradeWebImpl(
            setting = get(BACKEND2_SETTING),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}