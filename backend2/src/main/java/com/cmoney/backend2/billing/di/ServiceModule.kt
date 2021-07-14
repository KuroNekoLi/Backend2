package com.cmoney.backend2.billing.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.billing.service.BillingService
import com.cmoney.backend2.billing.service.BillingWeb
import com.cmoney.backend2.billing.service.BillingWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val billingServiceModule = module {
    single<BillingService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(BillingService::class.java)
    }
    single<BillingWeb> {
        BillingWebImpl(
            gson = get(BACKEND2_GSON),
            service = get(),
            setting = get(BACKEND2_SETTING)
        )
    }
}