package com.cmoney.backend2.identityprovider.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.identityprovider.service.IdentityProviderService
import com.cmoney.backend2.identityprovider.service.IdentityProviderWeb
import com.cmoney.backend2.identityprovider.service.IdentityProviderWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val identityProviderServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(IdentityProviderService::class.java)
    }
    single<IdentityProviderWeb> {
        IdentityProviderWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}