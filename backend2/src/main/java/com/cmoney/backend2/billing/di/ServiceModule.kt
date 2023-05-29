package com.cmoney.backend2.billing.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.billing.service.BillingService
import com.cmoney.backend2.billing.service.BillingWeb
import com.cmoney.backend2.billing.service.BillingWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val billingServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(BillingService::class.java)
    }
    single<BillingWeb> {
        BillingWebImpl(
            manager = get(),
            gson = get(BACKEND2_GSON),
            service = get()
        )
    }
}