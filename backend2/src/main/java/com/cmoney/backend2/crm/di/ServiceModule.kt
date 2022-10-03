package com.cmoney.backend2.crm.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.crm.service.CrmService
import com.cmoney.backend2.crm.service.CrmWeb
import com.cmoney.backend2.crm.service.CrmWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val crmServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(CrmService::class.java)
    }
    single<CrmWeb> {
        CrmWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}