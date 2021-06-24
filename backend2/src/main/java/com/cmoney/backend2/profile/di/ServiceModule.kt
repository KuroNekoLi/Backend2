package com.cmoney.backend2.profile.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.profile.service.ProfileService
import com.cmoney.backend2.profile.service.ProfileWeb
import com.cmoney.backend2.profile.service.ProfileWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val profileServiceModule = module {
    single<ProfileService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(ProfileService::class.java)
    }
    single<ProfileWeb> {
        ProfileWebImpl(get(BACKEND2_GSON), get(), get(BACKEND2_SETTING))
    }
}