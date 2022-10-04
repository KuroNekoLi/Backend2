package com.cmoney.backend2.additioninformationrevisit.di

import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitService
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWebImpl
import com.cmoney.backend2.additioninformationrevisit.service.ServicePath
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val BACKEND_ADDITION_INFORMATION_REVISIT_SERVICE = named("AdditionInformationRevisitService")

val additionalInformationRevisitServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(AdditionalInformationRevisitService::class.java)
    }
    single<AdditionalInformationRevisitWeb>(BACKEND_ADDITION_INFORMATION_REVISIT_SERVICE) { (servicePath: ServicePath) ->
        AdditionalInformationRevisitWebImpl(setting = get(BACKEND2_SETTING), service = get(), servicePath = servicePath)
    }
    single<AdditionalInformationRevisitWeb> {
        AdditionalInformationRevisitWebImpl(setting = get(BACKEND2_SETTING), service = get())
    }
}