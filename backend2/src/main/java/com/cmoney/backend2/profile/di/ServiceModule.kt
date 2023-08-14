package com.cmoney.backend2.profile.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.profile.service.ProfileService
import com.cmoney.backend2.profile.service.ProfileWeb
import com.cmoney.backend2.profile.service.ProfileWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val profileServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ProfileService::class.java)
    }
    single<ProfileWeb> {
        ProfileWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}