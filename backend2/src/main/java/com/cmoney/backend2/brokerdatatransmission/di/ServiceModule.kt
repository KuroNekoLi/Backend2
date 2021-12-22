package com.cmoney.backend2.brokerdatatransmission.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionService
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWeb
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val brokerDataTransmissionServiceModule = module {
    single<BrokerDataTransmissionService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(BrokerDataTransmissionService::class.java)
    }
    single<BrokerDataTransmissionWeb> {
        BrokerDataTransmissionWebImpl(
            service = get(),
            setting = get(BACKEND2_SETTING),
            gson = get(BACKEND2_GSON)
        )
    }
}