package com.cmoney.backend2.brokerdatatransmission.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionService
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWeb
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val brokerDataTransmissionServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(BrokerDataTransmissionService::class.java)
    }
    single<BrokerDataTransmissionWeb> {
        BrokerDataTransmissionWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}
