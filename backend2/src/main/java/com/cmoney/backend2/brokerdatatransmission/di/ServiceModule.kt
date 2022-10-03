package com.cmoney.backend2.brokerdatatransmission.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionService
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWeb
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val brokerDataTransmissionServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(BrokerDataTransmissionService::class.java)
    }
    single<BrokerDataTransmissionWeb> {
        val setting: Setting = get(BACKEND2_SETTING)
        BrokerDataTransmissionWebImpl(
            baseHost = setting.domainUrl,
            service = get(),
            setting = setting,
            gson = get(BACKEND2_GSON)
        )
    }
}
