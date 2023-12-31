package com.cmoney.backend2.virtualtrading2.di

import com.cmoney.backend2.base.di.BACKEND2_GSON_NON_SERIALIZE_NULLS
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2WebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val virtualTrading2ServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2).create(VirtualTrading2Service::class.java)
    }
    single<VirtualTrading2Web> {
        VirtualTrading2WebImpl(
            globalBackend2Manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON_NON_SERIALIZE_NULLS)
        )
    }
}