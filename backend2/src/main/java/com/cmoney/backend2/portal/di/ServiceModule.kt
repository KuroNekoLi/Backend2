package com.cmoney.backend2.portal.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.portal.service.PortalService
import com.cmoney.backend2.portal.service.PortalWeb
import com.cmoney.backend2.portal.service.PortalWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val portalServiceModule = module {
    single<PortalService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(PortalService::class.java)
    }
    single<PortalWeb> {
        PortalWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}