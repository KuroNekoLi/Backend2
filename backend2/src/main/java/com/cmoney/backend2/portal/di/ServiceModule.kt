package com.cmoney.backend2.portal.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.portal.service.PortalService
import com.cmoney.backend2.portal.service.PortalWeb
import com.cmoney.backend2.portal.service.PortalWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val portalServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(PortalService::class.java)
    }
    single<PortalWeb> {
        PortalWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}