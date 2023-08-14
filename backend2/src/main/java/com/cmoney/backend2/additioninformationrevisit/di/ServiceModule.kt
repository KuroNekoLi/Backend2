package com.cmoney.backend2.additioninformationrevisit.di

import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitService
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWebImpl
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val ADDITIONAL_INFORMATION_REVISIT_TW_WEB = named("additional_information_revisit_tw_web")
val ADDITIONAL_INFORMATION_REVISIT_US_WEB = named("additional_information_revisit_us_web")

val additionalInformationRevisitServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(AdditionalInformationRevisitService::class.java)
    }
    single<AdditionalInformationRevisitWeb>(qualifier = ADDITIONAL_INFORMATION_REVISIT_TW_WEB) {
        AdditionalInformationRevisitWebImpl(
            globalBackend2Manager = get(),
            service = get(),
            marketType = AdditionalInformationRevisitWeb.MarketType.TW
        )
    }
    single<AdditionalInformationRevisitWeb>(qualifier = ADDITIONAL_INFORMATION_REVISIT_US_WEB) {
        AdditionalInformationRevisitWebImpl(
            globalBackend2Manager = get(),
            service = get(),
            marketType = AdditionalInformationRevisitWeb.MarketType.US
        )
    }
}