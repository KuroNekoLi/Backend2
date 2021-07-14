package com.cmoney.backend2.authorization.di

import com.cmoney.backend2.authorization.service.AuthorizationService
import com.cmoney.backend2.authorization.service.AuthorizationWeb
import com.cmoney.backend2.authorization.service.AuthorizationWebImpl
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import org.koin.dsl.module
import retrofit2.Retrofit

val authorizationServiceModule = module {
    single<AuthorizationService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(AuthorizationService::class.java)
    }
    single<AuthorizationWeb> {
        AuthorizationWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}