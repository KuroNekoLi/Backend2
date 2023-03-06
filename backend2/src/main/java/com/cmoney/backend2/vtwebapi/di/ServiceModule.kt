package com.cmoney.backend2.vtwebapi.di

import com.cmoney.backend2.base.di.BACKEND2_GSON_NON_SERIALIZE_NULLS
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2
import com.cmoney.backend2.vtwebapi.service.VirtualTradeService
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWeb
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val virtualTradeServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2).create(VirtualTradeService::class.java)
    }
    single<VirtualTradeWeb> {
        VirtualTradeWebImpl(
            globalBackend2Manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON_NON_SERIALIZE_NULLS)
        )
    }
}