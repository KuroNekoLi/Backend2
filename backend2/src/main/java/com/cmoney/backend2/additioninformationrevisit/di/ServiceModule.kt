package com.cmoney.backend2.additioninformationrevisit.di

import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitService
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWebImpl
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import org.koin.dsl.module
import retrofit2.Retrofit

val additionalInformationRevisitServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(AdditionalInformationRevisitService::class.java)
    }
    single<AdditionalInformationRevisitWeb> {
        AdditionalInformationRevisitWebImpl(globalBackend2Manager = get(), service = get())
    }
}