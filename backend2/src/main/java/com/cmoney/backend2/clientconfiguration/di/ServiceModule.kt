package com.cmoney.backend2.clientconfiguration.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.clientconfiguration.service.ClientConfigurationService
import com.cmoney.backend2.clientconfiguration.service.ClientConfigurationWeb
import com.cmoney.backend2.clientconfiguration.service.ClientConfigurationWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val clientConfigurationModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ClientConfigurationService::class.java)
    }
    single<ClientConfigurationWeb> {
        ClientConfigurationWebImpl(
            manager = get(),
            service = get(),
            jsonParser = get(BACKEND2_GSON)
        )
    }
}